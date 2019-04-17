# Use-Case Specification: Show Subtask

# 1. Use-Case Name

# 1.1 Brief Description

This use case allows the user to show subtasks of deadlines in the overview.

# 2. Flow of Events

## 2.1 Basic Flow

### 2.1.1 Activity Diagram

![Flow-Diagram](./flowDiagram.jpg)

### 2.1.2 Mock-Up

![Mockup-Diagram](./mockup.jpg)

### 2.1.3 Feature

n.a.

## 2.2 Alternative Flows

n.a.

# 3. Special Requirements

## 3.1 Screen size support

Since the app can be used on every android phone, there will be many different screen sizes. The layout should not waste space on big screens and should still be readable on small screens.

# 4. Preconditions

## 4.1 App opened on screen

To show a subtask in the deadline overview, the app must be running and opened on the screen. 

## 4.2 The subtask to be shown is already created

To show a subtask in the deadline overview, it has to exist. 

# 5. Postconditions

## 5.1 Managing success
Subtask can be shown in the deadline overview. 

## 5.2 Managing failure

Display a notification, saying that and why (if possible) displaying the subtask failed.

# 6. Extension Points

n.a.

## 7. Function Point calculation
|transaction|DET|RET|FTR|Complexity|
|---|---|---|---|---|
|external input|1|||low|
|external output||1|1|low|
|external inquieries||||low|
|internal logical files|||1|low|
|external interface files||||low|

This makes 5,88 FP