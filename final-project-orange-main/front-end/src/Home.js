import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
//import trashIcon from './trash.png';

export const Home = () => {
    const [userName, setUserName] = useState('');
    const [email, setEmail] = useState(''); 
    const [message, setMessage] = useState('');
    const [contacts, setContacts] = useState([]); 
    const [isMessageBoxOpen, setIsMessageBoxOpen] = useState(false);
    const [selectedContact, setSelectedContact] = useState(null);
    const [messageInput, setMessageInput] = useState('');
    const navigate = useNavigate();

    /*
    fetch("/getContacts")
        .then(response=>response.json())
        .then(result=>{
            result.data.forEach(contact=>{
                const newContact = {
                    userName: contact.displayName, 
                    email:contact.email 
                };
                setContacts([...contacts, newContact]);
            });
        })
        */


    function handleSubmit() {
        
        if (!(userName && email)) {
            setMessage('Please enter both username and email.');
            return;
        }

        fetch("/addContact", {
            body: JSON.stringify({
                email: email,
                displayName: userName
            }),
            method: 'POST'
        }).then(response=>response.json())
        .then(object=>{
            if (!object.status) return;

            const newContact = { userName, email };
            setContacts([...contacts, newContact]); 
            setUserName('');
            setEmail('');
            setMessage('Contact added successfully!');
        }).catch(()=>
            setMessage('Contact add failed on back-end')
        )
    }

    
    const handleDelete = (index) => {
        const updatedContacts = contacts.filter((_, i) => i !== index);
        setContacts(updatedContacts);
        setMessage('Contact deleted successfully!');
    };
    const handleSendMessage = (contact) => {
        setSelectedContact(contact); 
        setIsMessageBoxOpen(true);
    };
    const handleSend = () => {
        if (messageInput.trim()) {
            const messageData = {
                fromId: userName, 
                toId: selectedContact.userName,
                message: messageInput.trim()
            };
    
            
            fetch("/sendMessage", {
                method: 'POST',
                body: JSON.stringify(messageData),
            })
                .then((response) => response.json())
                .then((data) => {
                    console.log("API Response:", data);
                    if (data.status) {
                        setIsMessageBoxOpen(false);
                        setMessageInput('');
                        setMessage(`Message sent to ${selectedContact.userName}!`);
                    } else {
                        setMessage(data.message || 'Failed to send message. Please try again.');
                    }
                })
                .catch((error) => {
                    console.error('Error sending message:', error);
                    setMessage('An error occurred while sending the message.');
                });
        } else {
            setMessage('Please type a message.');
        }
    };

    const handleCancel = () => {
        setIsMessageBoxOpen(false);
        setMessageInput('');
    };
    

    return (
        <div className="App">
            
            <h1>Contact List</h1>
            <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', marginBottom: '20px' }}>
                <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'flex-start', width: '100%', maxWidth: '400px' }}>
                    <div style={{ marginBottom: '10px', width: '100%', display: 'flex', alignItems: 'center' }}>
                        <label style={{ width: '100px' }}>User name:</label> 
                        <input 
                            value={userName} 
                            onChange={(e) => setUserName(e.target.value)} 
                            style={{ marginLeft: '5px', flex: 1 }} 
                        />
                    </div>
                    <div style={{ marginBottom: '10px', width: '100%', display: 'flex', alignItems: 'center' }}>
                        <label style={{ width: '100px' }}>Email:</label> 
                        <input 
                            value={email} 
                            onChange={(e) => setEmail(e.target.value)} 
                            style={{ marginLeft: '5px', flex: 1 }} 
                        />
                    </div>
                    <button onClick={handleSubmit} style={{ width: '100%' }}>Add Contact</button>
                </div>
                <div>{message}</div>
            </div>          
            <div style={{ marginTop: '20px' }}>
                {contacts.map((contact, index) => (
                    <div key={index} style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', marginBottom: '10px' }}>
                         <img 
                            //src={trashIcon} 
                            alt="Delete" 
                            style={{ width: '20px', height: '20px', cursor: 'pointer' }} 
                            onClick={() => handleDelete(index)} 
                        />
                        <div>
                            {contact.userName} | {contact.email}
                        </div>
                        <div>
                            <button onClick={()=>handleSendMessage(contact)}  style={{ width: '100%' }}> Send Message</button>
                        </div>
                       
                    </div>
                ))}
            </div>
                {isMessageBoxOpen && (
                <div style={{ position: 'fixed', top: '0', left: '0', right: '0', bottom: '0', backgroundColor: 'rgba(0, 0, 0, 0.5)', display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
                    <div style={{ backgroundColor: 'white', padding: '20px', borderRadius: '8px', width: '300px' }}>
                        <h2>Send Message to {selectedContact?.userName}</h2>
                        <textarea
                            value={messageInput}
                            onChange={(e) => setMessageInput(e.target.value)}
                            rows="4"
                            style={{ width: '100%', marginBottom: '10px' }}
                        />
                        <div style={{ display: 'flex', justifyContent: 'space-between' }}>
                            <button onClick={handleSend} style={{ width: '48%' }}>Send</button>
                            <button onClick={handleCancel} style={{ width: '48%' }}>Cancel</button>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
};
