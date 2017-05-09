abstract class Token {
}

class True extends Token{
	public String toString(){
		return "true";
	}
}

class False extends Token{
	public String toString(){
		return "false";
	}
}

class And extends Token{
	public String toString(){
		return "&&";
	}
}
class Or extends Token{
	public String toString(){
		return "||";
	}
}

class VarName extends Token {
	public String toString() {
		return "VarName(" + name + ")";
	}

	public final String name;

	public VarName(String name) {
		this.name = name;
	}
}

class Assign extends Token {
	public String toString() {
		return "Assign";
	}
}

class Number extends Token {
	public final int value;

	public String toString() {
		return "Number(" + value + ")";
	}

	public Number(int value) {
		this.value = value;
	}
}

class Minus extends Token {
	public String toString() {
		return "Minus";
	}
}

class Plus extends Token {
	public String toString() {
		return "Plus";
	}
}

class Times extends Token{
	public String toString(){
		return "Times";
	}
}

class Divide extends Token{
	public String toString(){
		return "Divide";
	}
}

class Semicolon extends Token {
	public String toString() {
		return "Semicolon";
	}
}

class While extends Token {
	public String toString() {
		return "While";
	}
}

class Lbr extends Token {
	public String toString() {
		return "Left bracket";
	}
}

class Rbr extends Token {
	public String toString() {
		return "Right bracket";
	}
}

class Lcurl extends Token {
	public String toString() {
		return "Left curl";
	}
}

class Rcurl extends Token {
	public String toString() {
		return "Right curl";
	}
}
