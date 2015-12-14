----- Construction des tables de la base de donn�es de production (APP), le sch�ma APP doit �tre d�j� cr�� -----

CREATE TABLE APP.GAME_CATEGORY (
	ID 				SMALLINT NOT NULL GENERATED ALWAYS AS IDENTITY,
	CATEGORY		VARCHAR(20),
	PRIMARY KEY (ID)
);

CREATE TABLE APP.GAME_EDITOR (
	ID 				SMALLINT NOT NULL GENERATED ALWAYS AS IDENTITY,
	NAME			VARCHAR(20),
	PRIMARY KEY (ID)
);

CREATE TABLE APP.GAME (
	ID 				SMALLINT NOT NULL GENERATED ALWAYS AS IDENTITY,
	NAME 			VARCHAR(30),
	DESCRIPTION		VARCHAR(1000),
	PUBLISHING_YEAR INT,
	MINIMUM_AGE		INT,
	MINIMUM_PLAYERS INT,
	MAXIMUM_PLAYERS INT,
	CATEGORY_ID		SMALLINT NOT NULL,
	EDITOR_ID		SMALLINT NOT NULL,
	PRIMARY KEY (ID),
	FOREIGN KEY (CATEGORY_ID) REFERENCES APP.GAME_CATEGORY(ID),
	FOREIGN KEY (EDITOR_ID) REFERENCES APP.GAME_EDITOR(ID)
);

CREATE TABLE APP.ITEM (
	ID 				SMALLINT NOT NULL GENERATED ALWAYS AS IDENTITY,
	COMMENTS		VARCHAR(1000),
	GAME_ID			SMALLINT NOT NULL,
	PRIMARY KEY (ID),
	FOREIGN KEY (GAME_ID) REFERENCES APP.GAME(ID)
);

CREATE TABLE APP.POSTAL_ADDRESS (
	ID 				SMALLINT NOT NULL GENERATED ALWAYS AS IDENTITY,
	STREET_ADDRESS	VARCHAR(100),
	POSTAL_CODE		VARCHAR(5),
	CITY			VARCHAR(30),
	COUNTRY			VARCHAR(20),
	PRIMARY KEY (ID)
);

CREATE TABLE APP.MEMBER_CREDENTIALS (
	ID 				SMALLINT NOT NULL GENERATED ALWAYS AS IDENTITY,
	SAVE_BORROWING	BOOLEAN,
	SAVE_BOOKING	BOOLEAN,
	MANAGE_GAMES	BOOLEAN,
	MANAGE_MEMBERS	BOOLEAN,
	EDIT_CONTEXTS	BOOLEAN,
	EDIT_PARAMETERS	BOOLEAN,
	PRIMARY KEY (ID)
);

CREATE TABLE APP.MEMBER_CONTEXT (
	ID 						SMALLINT NOT NULL GENERATED ALWAYS AS IDENTITY,
	NB_DELAYS				INT,
	NB_FAKE_BOOKINGS		INT,
	LAST_SUBSCRIPTION_DATE	DATE,
	CAN_BORROW				BOOLEAN,
	CAN_BOOK				BOOLEAN,
	PRIMARY KEY (ID)
);

CREATE TABLE APP.MEMBER (
	ID 					SMALLINT NOT NULL GENERATED ALWAYS AS IDENTITY,
	FIRST_NAME			VARCHAR(20),
	LAST_NAME			VARCHAR(30),
	PSEUDO				VARCHAR(20),
	BIRTH_DATE			DATE,
	PHONE_NUMBER		INT,
	EMAIL_ADDRESS		VARCHAR(40),
	POSTAL_ADDRESS_ID	SMALLINT NOT NULL,
	CREDENTIALS_ID		SMALLINT NOT NULL,
	CONTEXT_ID			SMALLINT NOT NULL,
	PRIMARY KEY (ID),
	FOREIGN KEY (POSTAL_ADDRESS_ID) REFERENCES APP.POSTAL_ADDRESS(ID),
	FOREIGN KEY (CREDENTIALS_ID) REFERENCES APP.MEMBER_CREDENTIALS(ID),
	FOREIGN KEY (CONTEXT_ID) REFERENCES APP.MEMBER_CONTEXT(ID)
);

CREATE TABLE APP.BORROW (
	MEMBER_ID		SMALLINT NOT NULL,
	ITEM_ID			SMALLINT NOT NULL,
	START_DATE		DATE NOT NULL,
	END_DATE		DATE NOT NULL,
	PRIMARY KEY (MEMBER_ID, ITEM_ID, START_DATE),
	FOREIGN KEY (MEMBER_ID) REFERENCES APP.MEMBER(ID),
	FOREIGN KEY (ITEM_ID) REFERENCES APP.ITEM(ID)
);

CREATE TABLE APP.BOOK (
	MEMBER_ID		SMALLINT NOT NULL,
	ITEM_ID			SMALLINT NOT NULL,
	START_DATE		DATE NOT NULL,
	END_DATE		DATE NOT NULL,
	PRIMARY KEY (MEMBER_ID, ITEM_ID, START_DATE),
	FOREIGN KEY (MEMBER_ID) REFERENCES APP.MEMBER(ID),
	FOREIGN KEY (ITEM_ID) REFERENCES APP.ITEM(ID)
);