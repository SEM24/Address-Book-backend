# Address Book Application
Address Book Application is a web-based application that allows users to manage their contacts. Users can perform various operations such as adding new contacts, editing existing contacts, reviewing contacts, deleting contacts, and importing/exporting contacts in JSON format.

## Implemented Methods

The following methods have been implemented in the codebase:

| Task                               | Description                                                     | Controller Method                     |
|------------------------------------|-----------------------------------------------------------------|---------------------------------------|
| Register in the app                | Allows users to register in the application with a login and password | `registration()` (UserController)     |
| Login to the app                   | Enables users to log in to the application                      | `signIn()` (UserController)           |
| Add new contact                    | Provides users with the ability to add a new contact            | `addContact()` (ContactController)     |
| Edit existing contact              | Allows users to edit the details of an existing contact         | `editContact()` (ContactController)    |
| Delete existing contact            | Enables users to delete an existing contact                     | `deleteContact()` (ContactController)  |
| Get list of existing contacts      | Retrieves the list of existing contacts for a user              | `showContacts()` (ContactController)   |

### Custom Contact Validations and Tests
In this application, custom annotations are used to validate the phone numbers, emails, and Latin names for contacts. These annotations ensure that the provided contact information meets specific requirements and enhances the data integrity and accuracy within the system.

For example:

```@ValidPhone```: Validates the phone numbers to match a specific pattern (+380-YY-XXXXXXX).

```@ValidEmail```: Validates the email addresses to meet standard email format (example@gmail.com).

```@ValidLatinName```: Validates the names to contain only Latin characters.

These custom annotations are utilized throughout the contact-related operations to ensure the validity of the provided data.

Also there were implemented ContactServiceTest and UserServiceTest to cover public methods.
## Additional Tasks

The following additional tasks have been implemented:

- Swagger Documentation: Added Swagger documentation to the project for easy API reference and testing. Can be accessed through ```http://localhost:8081/swagger-ui/index.html#/```
- Export/Import Contacts: Implemented the ability to export and import contacts in JSON format.
## How to Run the Project

To run the project locally, follow these steps:

1. Clone the repository to your local machine: ```https://github.com/SEM24/Address-Book-backend```
2. Make sure you have Java and Maven installed.
3. Open a terminal and navigate to the project's root directory.
4. Run the following command to build the project: `mvn clean install`.
5. Once the build is successful, run the following command to start the application: `mvn spring-boot:run`.
6. The application will start running on `http://localhost:8081`.
7. Access the application using a Postman or any other util.

## Files to help
In the project's root directory, you will find a folder named "data." Inside that folder, there is a JSON file that contains endpoints for Postman. You can use these endpoints to import contacts by using the provided "contacts.json" file.

## API Examples
Here are examples of HTTP requests for each method of the controllers:

### UserController

* Register in the app:
  * Method: POST
  * URL: `/auth`
  * Request body:
   ```
   {
  "username": "john.doe",
  "password": "password123"
   }
* Login to the app:
  * Method: POST
  * URL: `/auth/login`
  * Request body:
   ```
   {
  "username": "john.doe",
  "password": "password123"
   }
### ContactController

* Get list of existing contacts:
  * Method: GET
  * URL: `/contacts`

* Add new contact:
  * Method: POST
  * URL: `/contacts`
  * Request body:
   ```
   {
  "name": "John Doe",
  "emails": ["superstar@gmail.com", "breakingbad@gmail.com"],
  "phones": ["+380938735662","+380938735661", "+380938735667"]
   }
* Edit existing contact:
  * Method: POST
  * URL: `/contacts/{contact-id}`
  * Request body:
   ```
   {
  "name": "John Doe",
  "emails": ["superstar@gmail.com", "breakingbad@gmail.com"],
  "phones": ["+380938735662","+380938735661", "+380938735667"]
   }
 * Delete existing contact:
  * Method: DELETE
  * URL: `/contacts/{contact-id}`
### IOController

* Export contacts to JSON(must be authorized):
  * Method: GET
  * URL: `/contacts/export`
  * The file will be saved in data folder of project root.
* Import contacts from JSON:
  * Method: PUT
  * URL: `/contacts/import`
  * Request body(multipart/form-data):
   ```
  file: <path_to_json_file>
