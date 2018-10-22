# Use-Case Specification: <Use-Case Name>

# 1. Use-Case Name

\[The following template is provided for a Use-Case Specification, which contains the textual properties of the use case.  This document is used with a requirements management tool, such as Rational RequisitePro, for specifying and marking the requirements within the use-case properties.

The use-case diagrams can be developed in a visual modeling tool, such as Rational Rose.  A use-case report (with all properties) may be generated with Rational SoDA. For more information, see the tool mentors in the Rational Unified Process.\]

# 1.1 Brief Description

\[The description briefly conveys the purpose of the use case. A single paragraph will suffice for this description.\]
This use case allows the user to create a deadline. There are many text fields that can be filled out, containing information about the deadline. 
Some of the fields are mandatory, some are optional. The user can also choose to add or remove subtasks for the deadline that will be created.

# 2. Flow of Events

## 2.1 Basic Flow

\[This use case starts when the actor does something. An actor always initiates use cases.  The use case describes what the actor does and what the system does in response. It needs to be phrased in the form of a dialog between the actor and the system.

The use case describes what happens inside the system, but not how or why. If information is exchanged, be specific about what is passed back and forth. For example, it is not very illuminating to say that the actor enters customer information. It is better to say the actor enters the customer’s name and address. A Glossary of Terms is often useful to keep the complexity of the use case manageable—you may want to define things like customer information there to keep the use case from drowning in details.

Simple alternatives may be presented within the text of the use case. If it only takes a few sentences to describe what happens when there is an alternative, do it directly within the **Flow of Events** section. If the alternative flow is more complex, use a separate section to describe it. For example, an **Alternative Flow** subsection explains how to describe more complex alternatives.

A picture is sometimes worth a thousand words, though there is no substitute for clean, clear prose. If it improves clarity, feel free to paste graphical depictions of user interfaces, process flows or other figures into the use case. If a flow chart is useful to present a complex decision process, by all means use it! Similarly for state-dependent behavior, a state-transition diagram often clarifies the behavior of a system better than pages upon pages of text. Use the right presentation medium for your problem, but be wary of using terminology, notations or figures that your audience may not understand. Remember that your purpose is to clarify, not obscure.\]

## 2.2 Alternative Flows
n.a.

# 3. Special Requirements

##3.1 Screen size support

Since the app can be used on every android phone, there will be many different screen sizes.
The layout should not waste space on big screens and should still be readable on small screens.

# 4. Preconditions

## 4.1 App opened on screen
To add a new deadline the app must be running and open on the screen.

# 5. Postconditions

\[A postcondition of a use case is a list of possible states the system can be in immediately after a use case has finished.\]

## 5.1 Creation succesful
Creation view is closed and the list of deadlines, containing the newly created deadline is shown.

## 5.2 Creation failure
Display a notification, showing that and why (if possible) the creation of the deadline failed.


# 6. Extension Points

n.a.