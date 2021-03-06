package jBasic;

import dataTypes.J_Character;
import dataTypes.J_Integer;
import dataTypes.J_String;

/**
 * JVariableContainer,
 * A linked listed using nodes that contains all of the variables in a J-Basic program
 * @author Alex
 *
 */
public class JVariableContainer {
	Node head;
	JBasicRunner runner;
	
	JVariableContainer(JBasicRunner runner) {
		this.runner = runner;
		head = null;
	}
	
	/**
	 * This will add a new variable and keep it uninit 
	 * 'i' for int, 's' for string
	 * @param name
	 * @param varType
	 */
	public void addUninitVar(String name, char varType) {
		if(getVariable(name) != null) { //If this variable already exists ignore this command 
			return;
		}
		
		Node newNode = new Node(name, varType);
		if(head == null) head = newNode;
		else {
			Node placeNode = head;
			while(placeNode.nextNode != null) 
				placeNode = placeNode.nextNode;
			placeNode.nextNode = newNode;
		}
	}
	
	/**
	 * Adds a J String to the variable container
	 * @param name
	 * @param value
	 */
	public void addString(String name, String value) {
		if(getVariable(name) != null) { //If this variable already exists, update that value instead
			updateVariable(name, value); 
			return;
		}
		
		Node newNode = new Node(name, value);
		if(head == null) head = newNode;
		else {
			Node placeNode = head;
			while(placeNode.nextNode != null) {
				placeNode = placeNode.nextNode;
			}
			placeNode.nextNode = newNode;
		}
	}
	
	/**
	 * Adds a J integer to the variable container
	 * @param name
	 * @param value
	 */
	public void addInteger(String name, int value) {
		if(getVariable(name) != null) { //If this variable already exists, update that value instead
			updateVariable(name, value + ""); 
			return;
		}
		Node newNode = new Node(name, value);
		if(head == null) head = newNode;
		else {
			Node placeNode = head;
			while(placeNode.nextNode != null) {
				placeNode = placeNode.nextNode;
			}
			placeNode.nextNode = newNode;
		}
	}
	
	/**
	 * Adds a J Character to the variable container
	 * @param name
	 * @param value
	 */
	public void addCharacter(String name, char value) {
		if(getVariable(name) != null) { //If this variable already exists, update that value instead
			updateVariable(name, value + ""); 
			return;
		}
		Node newNode = new Node(name, value, true);
		if(head == null) head = newNode;
		else {
			Node placeNode = head;
			while(placeNode.nextNode != null) {
				placeNode = placeNode.nextNode;
			}
			placeNode.nextNode = newNode;
		}
	}
	
	/** 
	 * Updates an existing variable with a new value
	 * @param name
	 * @param newValue
	 */
	public void updateVariable(String name, String newValue) {
		if(head == null) return; //list is empty
		
		//Check the head first
		if(head.varName.equalsIgnoreCase(name)) {
			if(head.varType.equals("int")) {
				try {
					int value = Integer.parseInt(newValue);
					head.integer.setValue(value);	
					head.init = true;
				}
				catch(Exception e) {
					runner.throwError(5); //Type mismatch error
				}
			}
			else if(head.varType.equals("string")) {
				if(newValue.contains("\""))
					head.string.setValue(newValue.substring(1, newValue.length() - 1));
				else
					head.string.setValue(newValue);
				
				head.init = true;
			}
			else if(head.varType.equals("char")) {
				try {
					head.character.setValue(newValue.charAt(0));	
					head.init = true;
				}
				catch(Exception e) {
					runner.throwError(5); //Type mismatch error
				}
			}
		}
		
		Node placeNode = head;
		while(placeNode.nextNode != null) {
			if(placeNode.varName.equalsIgnoreCase(name)) {
				if(placeNode.varType.equals("int")) { //Integer updates
					try { //Convert from string to int
						int value = Integer.parseInt(newValue);
						placeNode.integer.setValue(value);
						placeNode.init = true;
					}
					catch(Exception e) { //throw error
						runner.throwError(5); //Type mismatch error
					}
				}
				else if(placeNode.varType.equals("string")) {//string updates
					if(newValue.contains("\""))
						placeNode.string.setValue(newValue.substring(1, newValue.length() - 1));
					else
						placeNode.string.setValue(newValue);
					
					placeNode.init = true;
				}
				else if(placeNode.varType.equals("char")) {
					try {
						placeNode.character.setValue(newValue.charAt(0));		
						placeNode.init = true;
					}
					catch(Exception e) {
						runner.throwError(5); //Type mismatch error
					}
				}
			}
			placeNode = placeNode.nextNode;
		}
		
		//Check the last element
		if(placeNode.varName.equalsIgnoreCase(name)) {
			if(placeNode.varType.equals("int")) {
				try {
					int value = Integer.parseInt(newValue);
					placeNode.integer.setValue(value);	
					placeNode.init = true;
				}
				catch(Exception e) {
					runner.throwError(5); //Type mismatch error
					
				}
			}
			else if(placeNode.varType.equals("string")) {
				if(newValue.contains("\""))
					placeNode.string.setValue(newValue.substring(1, newValue.length() - 1));
				else
					placeNode.string.setValue(newValue);
				
				placeNode.init = true;
			}
			else if(placeNode.varType.equals("char")) {
				try {
					placeNode.character.setValue(newValue.charAt(0));	
					placeNode.init = true;
				}
				catch(Exception e) {
					runner.throwError(5); //Type mismatch error
				}
			}
		}
	}
	
	/**
	 * Returns a integer with the given variable name
	 * @param name
	 * @return
	 */
	public J_Integer getInteger(String name) {
		if(head == null) return null; //list is empty
		
		//Check the head first
		if(head.varName.equalsIgnoreCase(name)) return head.integer;
		
		Node placeNode = head;
		while(placeNode.nextNode != null) {
			if(placeNode.varName.equalsIgnoreCase(name)) return placeNode.integer;
			placeNode = placeNode.nextNode;
		}
		//Check the last node
		if(placeNode.varName.equalsIgnoreCase(name)) return placeNode.integer;
		
		return null;
	}
	
	/**
	 * Returns a J String with the given variable name
	 * @param name
	 * @return
	 */
	public J_String getString(String name) {
		if(head == null) return null; //list is empty
		
		//Check the head first
		if(head.varName.equalsIgnoreCase(name)) return head.string;
		
		Node placeNode = head;
		while(placeNode.nextNode != null) {
			if(placeNode.varName.equalsIgnoreCase(name)) return placeNode.string;
			placeNode = placeNode.nextNode;
		}
		
		//Check the last node
		if(placeNode.varName.equalsIgnoreCase(name)) return placeNode.string;
		
		return null;
	}
	
	/**
	 * Returns a J Character with the given variable name
	 * @param name
	 * @return
	 */
	public J_Character getCharacter(String name) {
		if(head == null) return null; //list is empty
		
		//Check the head first
		if(head.varName.equalsIgnoreCase(name)) return head.character;
		
		Node placeNode = head;
		while(placeNode.nextNode != null) {
			if(placeNode.varName.equalsIgnoreCase(name)) return placeNode.character;
			placeNode = placeNode.nextNode;
		}
		
		//Check the last node
		if(placeNode.varName.equalsIgnoreCase(name)) return placeNode.character;
		
		return null;
	}
	
	/**
	 * This returns the value of a certain variable as a string.
	 * Data type does not matter here, returns null if variable does not exist.
	 * @param name
	 * @return
	 */
	public String getVariable(String name) {
		if(head == null) return null; //list is empty
		
		//Check the head first
		if(head.varName.equalsIgnoreCase(name)) {
			if(head.varType.equalsIgnoreCase("int")) 
				return head.integer.getValue() + "";
			else if(head.varType.equalsIgnoreCase("string")) 
				return head.string.getValue();
			else if(head.varType.equalsIgnoreCase("char")) 
				return head.character.getValue() + "";
		}
		
		Node placeNode = head;
		while(placeNode.nextNode != null) {
			if(placeNode.varName.equalsIgnoreCase(name)) {
				if(placeNode.varType.equalsIgnoreCase("int")) 
					return placeNode.integer.getValue() + "";
				else if(placeNode.varType.equalsIgnoreCase("string")) 
					return placeNode.string.getValue();
				else if(placeNode.varType.equalsIgnoreCase("char")) 
					return placeNode.character.getValue() + "";
			}
			placeNode = placeNode.nextNode;
		}
		//Check the last node
		if(placeNode.varName.equalsIgnoreCase(name)) {
			if(placeNode.varType.equalsIgnoreCase("int")) {
				return placeNode.integer.getValue() + "";
			}
			else if(placeNode.varType.equalsIgnoreCase("string")) 
				return placeNode.string.getValue();
			else if(placeNode.varType.equalsIgnoreCase("char")) 
				return placeNode.character.getValue() + "";
		}
		
		return null;
	}
	
	/**
	 * Returns all strings in the variable container as a J_String Array
	 * @return
	 */
	public J_String[] getStrings() {
		J_String[] strings = new J_String[0];
		Node placeNode = head;
		while(placeNode.nextNode != null) {
			if(placeNode.varType.equals("string")) {
				J_String[] newStrings = new J_String[strings.length + 1];
				for(int i = 0; i < strings.length; i++) {
					newStrings[i] = strings[i];
				}
				newStrings[newStrings.length - 1] = placeNode.string;
				strings = newStrings;
			}
			placeNode = placeNode.nextNode;
		}
		return strings;
	}
	
	/**
	 * Returns all integers in the variable container as a J_Integer array
	 * @return
	 */
	public J_Integer[] getIntegers() {
		J_Integer[] integers = new J_Integer[0];
		Node placeNode = head;
		while(placeNode.nextNode != null) {
			if(placeNode.varType.equals("int")) {
				J_Integer[] newIntegers = new J_Integer[integers.length + 1];
				for(int i = 0; i < integers.length; i++) {
					newIntegers[i] = integers[i];
				}
				newIntegers[newIntegers.length - 1] = placeNode.integer;
				integers = newIntegers;
			}
			placeNode = placeNode.nextNode;
		}
		return integers;
	}
	
	/**
	 * Returns all characters in the variable container as a J_Character array
	 * @return
	 */
	public J_Character[] getCharacters() {
		J_Character[] chars = new J_Character[0];
		Node placeNode = head;
		while(placeNode.nextNode != null) {
			if(placeNode.varType.equals("char")) {
				J_Character[] newChars = new J_Character[chars.length + 1];
				for(int i = 0; i < chars.length; i++) {
					newChars[i] = chars[i];
				}
				newChars[newChars.length - 1] = placeNode.character;
				chars = newChars;
			}
			placeNode = placeNode.nextNode;
		}
		return chars;
	}
	
	/**
	 * Determines if the variable in question has been init, returns that value
	 * @param name
	 * @return
	 */
	public boolean isInit(String name) {
		//Check the head first
		if(head.varName.equalsIgnoreCase(name)) return head.init;
		
		Node placeNode = head;
		while(placeNode.nextNode != null) {
			if(placeNode.varName.equalsIgnoreCase(name)) {
				return placeNode.init;
			}
			placeNode = placeNode.nextNode;
		}
		
		//Check the last node
		if(placeNode.varName.equalsIgnoreCase(name)) return placeNode.init;
		
		return false;
	}
	
	/**
	 * Node class. Used in the linked list for the JVariableContainer.
	 * @author Alex
	 *
	 */
	class Node {
		Node nextNode;
		J_Integer integer;
		J_String string;
		J_Character character;
		String varName;
		String varType;
		boolean init;
		
		
		Node(String varName, String value) {
			this.varName = varName;
			string = new J_String(varName, value);
			init = true;
			nextNode = null;
			varType = "string";
		}
		
		Node(String varName, int value) {
			this.varName = varName;
			integer = new J_Integer(varName, value);
			init = true;
			nextNode = null;
			varType = "int";
		}

		/**
		 * By passing in a value for isChar, you are saying that this value is a char
		 * @param varName
		 * @param value
		 * @param isChar
		 */
		Node(String varName, char value, boolean isChar) {
			this.varName = varName;
			character = new J_Character(varName, value);
			init = true;
			nextNode = null;
			varType = "char";
		}
		
		Node(String varName, char type) { //I for int S for string C for char
			this.varName = varName;
			if(type == 'i') {
				varType = "int";
				integer = new J_Integer(varName, Integer.MIN_VALUE);
			}
			else if(type == 's') {
				varType = "string";
				string = new J_String(varName, "UNDEFINED");
			}
			else if(type == 'c') {
				varType = "char";
				character = new J_Character(varName, Character.MIN_VALUE);
			}
			
			init = false;
			nextNode = null;
		}
		
	}
}
