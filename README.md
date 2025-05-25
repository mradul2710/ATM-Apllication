ATM Application
Overview
The ATM Application is a simple command-line Java program that simulates the operations of an Automated Teller Machine (ATM). It allows users to check their balance, deposit money, withdraw funds, and exit the application. This program is designed to demonstrate fundamental concepts of Java programming such as:

Control flow statements (switch, if, while)
Loops
User input handling using Scanner
Method creation and reuse
Encapsulation of business logic
Features
✅ Check account balance
💰 Deposit funds to the account
🏧 Withdraw money (with balance validation)
🔒 Exit the program safely
File Structure
ATMApplication.java
Contains all logic for the ATM simulation, including:
User interaction via a command-line menu
Input handling
Balance operations
Requirements
Java JDK 8 or higher
Terminal / Command Prompt or any Java IDE (Eclipse, IntelliJ IDEA, NetBeans)
How to Compile and Run
Open Terminal / Command Prompt
Navigate to the directory containing the file:
Program Workflow Once executed, the user is greeted with a menu:

Welcome to the ATM!

Check Balance
Deposit
Withdraw
Exit Enter your choice:
The user enters a number corresponding to their choice, and the application performs the following:

Method Descriptions

main(String[] args) Entry point of the program.
Uses a Scanner to read user input.

Displays a menu in a loop until the user chooses to exit.

Calls appropriate methods based on user choice.

checkBalance() Displays the current balance.
No parameters, no return value.

Purely for output.

deposit(double amount) Increases the balance by the amount specified.
Validates that the deposit amount is non-negative.

withdraw(double amount) Checks if the amount is less than or equal to the current balance.
If yes, deducts the amount; otherwise, prints an error.

Welcome to the ATM!

Check Balance
Deposit
Withdraw
Exit Enter your choice: 1 Your balance is: ₹5000.0
Enter your choice: 2 Enter deposit amount: 1000 ₹1000.0 deposited successfully.

Enter your choice: 3 Enter withdrawal amount: 2000 ₹2000.0 withdrawn successfully.

Enter your choice: 4 Thank you for using the ATM. Goodbye!

Code Highlights Uses a while(true) loop to keep showing the menu until the user exits.

Uses switch for clean control flow.

Uses static methods for ATM operations to keep the main method clean.

Balance is stored in a static variable for global accessibility.

Limitations No PIN-based authentication

Balance is not persistent (resets when the program ends)

Not thread-safe (not an issue in single-user CLI)

Possible Enhancements Add PIN/password verification

Store user data in a file or database

Support multiple user accounts

Implement object-oriented design with an Account class

License This project is intended for educational use only. No warranties or guarantees are provided. Feel free to modify and distribute for learning purposes.

Author Created as a basic simulation of ATM functionality in Java.
