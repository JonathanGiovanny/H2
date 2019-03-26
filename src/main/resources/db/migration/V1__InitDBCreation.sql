--
-- Create table TAGS
--
CREATE TABLE TAGS (
	TAG_ID			BIGINT			AUTO_INCREMENT(1,1),
	TAG_NAME		VARCHAR2(255)
);
CREATE INDEX IDX_TAG_ID ON TAGS(TAG_ID);
ALTER TABLE TAGS ADD CONSTRAINT TAGS_PK_TAG_ID PRIMARY KEY (TAG_ID);
--
-- Create table H_TYPE
--
CREATE TABLE H_TYPE (
	HT_ID			BIGINT			AUTO_INCREMENT(1,1),
	HT_NAME			VARCHAR2(255)
);
CREATE INDEX IDX_HT_ID ON H_TYPE(HT_ID);
ALTER TABLE H_TYPE ADD CONSTRAINT H_TYPE_PK_HT_ID PRIMARY KEY (HT_ID);
--
-- Create table H
--
CREATE TABLE H (
	H_ID				BIGINT			AUTO_INCREMENT(1,1),
	H_CLICKS			BIGINT,
	H_COVER				VARCHAR2(255),
	H_NAME				VARCHAR2(255),
	H_SCORE				INTEGER,
	H_URL				VARCHAR2(255),
	H_TYPE__HT_ID		INTEGER,
	CREATED_BY			VARCHAR2(255),
	CREATION_DATE		TIMESTAMP,
	MODIFIED_DATE		TIMESTAMP
);
CREATE INDEX IDX_H_ID ON H(H_ID);
ALTER TABLE H ADD CONSTRAINT H_PK_H_ID PRIMARY KEY (H_ID);
ALTER TABLE H ADD CONSTRAINT H_FK_HT_ID FOREIGN KEY (H_TYPE__HT_ID) REFERENCES H_TYPE(HT_ID);
--
-- Create table H_TAGS
--
CREATE TABLE H_TAGS (
	H_ID			BIGINT			AUTO_INCREMENT(1,1),
	TAG_ID			BIGINT			NOT NULL
);
ALTER TABLE H_TAGS ADD CONSTRAINT H_TAGS_PK_H_TAG PRIMARY KEY (H_ID, TAG_ID);
--
-- Create table H_HISTORY
--
CREATE TABLE H_HISTORY (
	HH_ID			BIGINT			AUTO_INCREMENT(1,1),
	HH_H_ID			BIGINT			NOT NULL,
	HH_DATE			TIMESTAMP
);
CREATE INDEX IDX_HH_H_ID ON H_HISTORY(HH_H_ID);
ALTER TABLE H_HISTORY ADD CONSTRAINT HH_PK_HH_H_ID PRIMARY KEY (HH_H_ID);