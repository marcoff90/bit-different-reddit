REDDIT APP

![Alt Text](https://github.com/green-fox-academy/marcoff90/blob/master/week-09/day-04/src/main/resources/static/img/readmegif/IMG_0767.gif)

- in all views - if the user is logged in, the username is rendered in the right corner

HANDLED BY POST CONTROLLER

Main page

- Render posts ordered by votes, then dates, 10 post per page, the newest post always first (when starting a new
  db, first post is made of stackoverflow)
- next to each post is rendered avatar of the user
- posts are as cards in two columns
- pagination at the bottom
    - controller takes path variable int page, this is sent to postService as a parameter and based
      on the value sublist is created (start point int end - 10 (starts at zero -> 10 -> 20 ...),
      end of sublist passed int page * 10)
        - the sublist is made within try/catch block, tries return start/end sublist, catches return
          sublist from start to all posts size
    - count of pages is sent through postservice as List of integers allPost size / 10, if any rest + 1;
- posts can be anonymous or by user, when by registered user, username is rendered
- upvoting/downvoting
    - For registered users
        - when user clicks on button upvote/downvote, new upvote/downvote object is instantiated (if
          the object with this post_id and user_id doesn't exist), boolean value if was
          upvoted/downvoted set to false, passed database also user_id & post_id
        - then upvote/downvote method is called, changes value of votes and set boolean of the vote
          type object to true - this stops user to upvote downvote post again
        - if user voted and tries again -> alert
    - For not registered users
        - alert - they have to register
- adding posts
    - pops up modal with submitting form
    - checks if the url isn't already in database
    - if user is logged in, adds user_id to the database with post and renders username in Posted by
    - if url in dtb -> alert
    - if added -> success alert
- link to comments section next to each post with icon
    - handled in comments controller

HANDLED BY USER CONTROLLER

- register user
    - checks if username isn't in database -> alert
    - saves user to database, set it as current user
    - username regex only letters, digits and -_.
    - password regex 8 characters minimum, 1 letter, 1 digit
    - redirects to home page
    - user chooses an avatar from 9 input images, the src is saved as a value of String avatar to db
      and then rendered at posts/comments made by user as well as when user is logged in, then next
      to his username in upper right corner
        - CURRENT USER - for rendering username - when loggout, set to null
- log in
    - checks if the passed parameters (username, password) are matched in database
        - sets as current user
        - if no match - alert -> back on login
        - if match - success alert -> home page
- logout
    - sets current user to null
    - logout alert

HANDLED BY COMMENTS CONTROLLER

- after clicking the icon -> redirect to id/comment
- shows comments for the post Id ordered by votes, then date
- shows top three comments, the rest is showed after clicking button more comments

upvoting/downvoting comments

- similar logic to upvoting/downvoting posts
- through dtb search for match of post id and user id, if no match
- upvote/downvote object is instantiated, boolean set to false
- through comment service comment is upvoted, value of boolean set to true
- saved through cascade type all
- when user isn't logged in -> alert when trying to vote
- when user is logged in and tries to vote again -> alert

adding comments

- pops up modal with submitting form
- can be done by anonymous or registered user
- alert is showed that comment was added
- no limit for number of comments per user

