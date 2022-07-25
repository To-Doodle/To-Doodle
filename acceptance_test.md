# Requirement checking step by step

#### This guide presents what Siyuan expects to see in terms of his work on the final version of the App
<hr>

#### Action
Register parent and child accounts
#### Expect
Up to member working on Authentication part.
<hr>

#### Action
Log in with __child__ account
#### Expect
Navigate to Todo fragment. 
A dialog with new achievement of `Time To Fight` pops up. 
The coin indicator becomes `10`.
<hr>

#### Action
Create a task. Then click done.
#### Expect
Navigate back to todo fragment. 
A dialog with new achievement of `Here You Go` pops up. 
The coin indicator becomes `20`. 
Task is showing on the page
<hr>

#### Action
Navigate to pending page
#### Expect
Empty page
<hr>

#### Action
Navigate back to todo page. Click the task viewbox
#### Expect
A dialog with the task info pops up. 
A close button and a __trash__ button show at the top right corner.
<hr>

#### Action
Click the trash button
#### Expect
Dialog closes with the text being stroke through
<hr>

#### Action
Navigate to pending page
#### Expect
Task is showing on the page
<hr>

#### Action
Click the task viewbox
#### Expect
A dialog with the task info pops up.
A close button __but no tick button__ show at the top right corner.
<hr>

#### Action
Navigate to doodle page. Draw a doodle and save (tick button)
#### Expect
A dialog asking for storage permission shows
<hr>

#### Action
Click `allow`
#### Expect
The drawing board is clearer and the doodle saved.
A dialog with new achievement of `Set Sail` pops up.
A dialog with new achievement of `Harvest` pops up.
The coin indicator becomes `40`.
<hr>

#### Action
Log out and log in with __parent__ account
#### Expect
Same task as the child account shown.
A dialog with new achievement of `Child Behaves` pops up.
The coin indicator becomes `50`.
<hr>

#### Action
Navigate to pending page
#### Expect
Task is showing on the page
<hr>

#### Action
Click the task viewbox
#### Expect
A dialog with the task info pops up.
A close button and a __tick button__ show at the top right corner.
<hr>

#### Action
Click the tick button
#### Expect
Dialog closes with the text being stroke through.
A dialog with new achievement of `Job Done` pops up.
The coin indicator becomes `60`.
<hr>

#### Action
Navigate to achievements page
#### Expect
6 achievements are color themed (those we just achieved)
<hr>

#### Action
Navigate to rewards page
#### Expect
Only the first reward (toms coffee) is redeemable.
<hr>

#### Action
Click the redeem button
#### Expect
A dialog with some text and a 4-digit random code is shown.
<hr>