INSERT INTO OAUTH_CLIENT_DETAILS(CLIENT_ID, RESOURCE_IDS, CLIENT_SECRET, SCOPE, AUTHORIZED_GRANT_TYPES, AUTHORITIES, ACCESS_TOKEN_VALIDITY, REFRESH_TOKEN_VALIDITY)
VALUES ('docmanager-react-client', 'docmanager-react-rest-Id',
        /*Password1!*/'$2a$16$yratGzeLZvnbz4tQER7OAefaEuWb76guVM4H60yWeoIRA1CsKyvHq',
        'read,write', 'password,authorization_code,refresh_token,implicit', 'USER', 10800, 2592000);

INSERT INTO AUTHORITY(ID, NAME) VALUES (1, 'STANDARD_USER');
INSERT INTO AUTHORITY(ID, NAME) VALUES (2, 'ADMIN_USER');

INSERT INTO USER_(ID, USER_NAME, PASSWORD, ACCOUNT_EXPIRED, ACCOUNT_LOCKED, CREDENTIALS_EXPIRED, ENABLED)
VALUES (1, 'admin.admin', /*Password1!*/'$2a$16$pwTtSYKf1OvZEGkX4Y2ASujuR1tx.tZygqrOu8UOVtW8Ta0op3elK', FALSE, FALSE, FALSE, TRUE);

INSERT INTO USER_(ID, USER_NAME, PASSWORD, ACCOUNT_EXPIRED, ACCOUNT_LOCKED, CREDENTIALS_EXPIRED, ENABLED)
VALUES (2, 'bode.idowu', /*Password1!*/'$2a$16$YR466acUsvZokcgkJsC12eBjp85ckezwCqQa8qLRpgPsXxL8B4OuO', FALSE, FALSE, FALSE, TRUE);

INSERT INTO USERS_AUTHORITIES(USER_ID, AUTHORITY_ID) VALUES (1, 1);
INSERT INTO USERS_AUTHORITIES(USER_ID, AUTHORITY_ID) VALUES (1, 2);
INSERT INTO USERS_AUTHORITIES(USER_ID, AUTHORITY_ID) VALUES (2, 2);