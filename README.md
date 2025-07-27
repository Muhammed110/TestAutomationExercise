# TestAutomationExercise

A Selenium-based UI automation test suite for the [AutomationExercise](https://automationexercise.com) website, using Java, TestNG, and the Page Object Model design pattern.

## 📌 Project Overview

This project demonstrates automated UI testing for a real e-commerce-like web application. It includes test scenarios such as:
- Login with valid and invalid credentials
- Adding and removing products
- Verifying cart contents
- Contact form submission
- Product search and validation

## 🧪 Tech Stack

- **Programming Language**: Java
- **Automation Tool**: Selenium WebDriver
- **Test Framework**: TestNG
- **Design Pattern**: Page Object Model (POM)
- **Build Tool**: Maven
- **Browser Support**: Chrome (via WebDriver)
- **Utilities**: Properties file, Driver Factory

## 📁 Project Structure


## 🚀 Getting Started


## 1. Clone the Repository

```bash
git clone https://github.com/Muhammed110/TestAutomationExercise.git
cd TestAutomationExercise
```

## 2. Install Dependencies

```bash
mvn clean install
```

## 3. Run Tests

You can execute the test suite using TestNG via the XML suite file:

```bash
mvn test -DsuiteXmlFile=testng.xml
```

## 📷 Future Enhancements

- Add screenshot capture on failure  
- Integrate reporting tools (e.g., Allure, ExtentReports)  
- Add CI/CD support via GitHub Actions  
- Implement API testing layer  

## 🙋‍♂️ Author

**Muhammed Ayman**  
[GitHub Profile](https://github.com/Muhammed110)
