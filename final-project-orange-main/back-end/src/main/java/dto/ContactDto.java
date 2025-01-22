package dto;

import org.bson.Document;
// import org.bson.types.ObjectId;

public class ContactDto extends BaseDto {
	// STATES
	private String email,
		displayName,
		owner;

	// CONSTRUCTORS
	public ContactDto(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	
	public ContactDto() {}
	
	// METHODS
	public String getEmail(){return email;}
	public String getOwner(){return owner;}
	
	public String getDisplayName(){return displayName;}

	public void setEmail(String e){email=e;}
	public void setOwner(String o){owner=o;}
	
	public void setDisplayName(String d){displayName=d;}

	@Override
	public Document toDocument() {
		return new Document("owner", owner)
			.append("email", email)
			.append("displayName", displayName);
		// do something with uniqueid?
	}

	// FUNCTIONS
	public static ContactDto fromDocument(Document doc) {
		var result = new ContactDto();
		result.setDisplayName(doc.getString("displayName"));
		result.setEmail(doc.getString("email"));
		result.setOwner(doc.getString("owner"));
		
		return result;
	}
	
}
