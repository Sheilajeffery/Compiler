abstract class Value {}

    class IntValue extends Value {
        public int value;

        public IntValue(int value) {
            this.value = value;
        }
        public String toString() {
            return this.value+"";
        }
        public boolean equals(Object other) {
            return other instanceof IntValue && ((IntValue) other).value == this.value;
        }
    }

    class BoolValue extends Value {
        public boolean value;

        public BoolValue(boolean value) {
            this.value = value;
        }
        public String toString() {
            return this.value+"";
        }
        public boolean equals(Object other) {
            return other instanceof BoolValue && ((BoolValue) other).value == this.value;
        }
    }
