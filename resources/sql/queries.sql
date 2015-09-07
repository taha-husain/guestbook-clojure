--name:save-message!
-- creates a new message
INSERT INTO guestbook
(name, message, timestamp)
VALUES (:name, :message, :timestamp)

--name:get-messages
-- selects all available messages
SELECT * from guestbook

--name:delete-message!
-- deletes a message
DELETE FROM guestbook
WHERE id = :id

--name:create-user!
-- creates a new user
INSERT INTO users
(username, password, first_name, last_name)
VALUES (:username, :password, :first, :last)
RETURNING id

--name:find-user
-- select all users
SELECT * from users
WHERE id = :id
