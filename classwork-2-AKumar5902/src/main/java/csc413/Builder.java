package csc413;

class ObjectToBuild {

    int fieldA;
    int fieldB;
    int fieldC;
    int fieldD;
    int fieldE;

    public ObjectToBuild(int fieldA, int fieldB, int fieldC, int fieldD, int fieldE) {
        //Step 1
        this.fieldA = fieldA;
        this.fieldB = fieldB;
        this.fieldC = fieldC;
        this.fieldD = fieldD;
        this.fieldE = fieldE;
    }

    public static class Builder {
        int fieldA;
        int fieldB;
        int fieldC;
        int fieldD;
        int fieldE;

        //Step 2 generate Setters
        public Builder setFieldA(int fieldA) {
            this.fieldA = fieldA;
            return this;
        }

        public Builder setFieldB(int fieldB) {
            this.fieldB = fieldB;
            return this;
        }


        public Builder setFieldC(int fieldC) {
            this.fieldC = fieldC;
            return this;
        }

        public Builder setFieldD(int fieldD) {
            this.fieldD = fieldD;
            return this;
        }

        public Builder setFieldE(int fieldE) {
            this.fieldE = fieldE;
            return this;
        }


        //Step 3
        public ObjectToBuild build() {
            return new ObjectToBuild(fieldA, fieldB, fieldC, fieldD, fieldE);
        }

        public static void main(String[] args) {
            Builder builder = new Builder();
            builder.setFieldA(3);
            builder.setFieldB(2);
            builder.setFieldC(1);

            //chainable setter
            builder.setFieldC(3)
                    .setFieldB(6)
                    .setFieldE(10)
                    .setFieldA(5)
                    .setFieldD(1);

            ObjectToBuild objectToBuild = builder.build();
            System.out.println(objectToBuild);
        }
    }
}

