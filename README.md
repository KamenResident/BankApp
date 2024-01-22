## BankApp

An application used to simulate a basic banking system that keeps track of its users.

## Built With

* Java[javax.swing]

## Getting Started

### Prerequisite

* Must have Java SE 8 or higher to run.
* An IDE ready to use.

### Installation

1. Clone the following repo:

   ```sh
   git clone https://github.com/KamenResident/BankApp.git
   ```

2. Then run the application in the IDE of your choice from MainLauncher.java.

## Usage

Ensure that you are running the application starting from MainLauncher.java.
For the sign up window, ensure that your password is longer than 6 characters and has at least each of the following:

* Uppercase letter
* Lowercase letter
* Digit
* Special character

## Roadmap

- [x] Implement the log in window, and its UI and features.
- [x] Implement the sign up window, and its UI and features.
- [x] Implement the main banking application's window and its UI.
  - [x] Add the feature for transferring money between two accounts.
  - [x] Implement the withdraw and deposit features.
    - [x] Add different restrictions depending on the account type (checking or savings).
  - [x] Add unique functionality to the checking and savings accounts.
  - [x] Add another tab for viewing the current user's profile.
- [ ] Change data type for money to BigDecimal for the entire application.
- [ ] Create a local database for the application itself.

