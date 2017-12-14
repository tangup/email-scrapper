# email-scrapper

# Project Title
A command line program maven project that will take an internet domain name and print out a list of the email addresses that were found on that website only.
## Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.
### Prerequisites
What things you need to install the software and how to install them
-- Maven
or
--Eclipse IDE
### Installing
A step by step guide for development env running
The step will be:
1. Download Maven from https://maven.apache.org/
2. Extract and set path.
2. Verify Maven installation:
3. Windows
Open Command Console
c:\> mvn --version
Linux
Open Command Terminal
$ mvn --version
Mac
Open Terminal
machine:~ Tanvi$ mvn --version
4. mvn clean/mvn clean install.
### Running
-- navigate to target folder of the project.
java -cp email-scrapper-0.0.1-SNAPSHOT.jar com.emailScrapper.ScrapperTest https://shop.nordstrom.com/

### Output
****************************

Extracted Email Addresses:


****************************
david.j.casados@nordstrom.com
giving@nordstrom.com
privacy@nordstrom.com
csr@nordstrom.com
Wilkossteve.wilkos@nordstrom.comVisit
onlinefraud@nordstrom.com
address@gmail.com
Grossbrandon.gross@nordstrom.comCanada
nordcardservmess@nordstrom.com
styleboards@nordstrom.com

## Built With
* [Jsoup](https://jsoup.org/) - API for extracting and manipulating data
* [Maven](https://maven.apache.org/) - Dependency Management