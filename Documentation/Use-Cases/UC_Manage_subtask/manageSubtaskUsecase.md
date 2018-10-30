# Use-Case Specification: Manage deadline

# 1. Use-Case Name

# 1.1 Brief Description

This use case allows the user to manage a subtask, containing the name, if it is already done and the option to delete it.

# 2. Flow of Events

## 2.1 Basic Flow

### 2.1.1 Activity Diagram

![Flow-Diagram](./flowDiagram.JPG)

### 2.1.2 Mock-Up

![Mockup-Diagram](./mockup.JPG)

### 2.1.3 Feature

[Feature](/app/src/androidTest/assets/manageDeadline.feature)

## 2.2 Alternative Flows

n.a.

# 3. Special Requirements

## 3.1 Screen size support

Since the app can be used on every android phone, there will be many different screen sizes. The layout should not waste space on big screens and should still be readable on small screens.

# 4. Preconditions

## 4.1 App opened on screen

To manage a subtask the app must be running and opened on the screen. 

## 4.2 The deadline to be managed is already created

To manage a subtask, it has to exist. 

# 5. Postconditions

## 5.1 Managing success
Subtask can be shown in the deadline overview. 

## 5.2 Managing failure

Display a notification, saying that and why (if possible) the managing failed.

# 6. Extension Points

n.a.
