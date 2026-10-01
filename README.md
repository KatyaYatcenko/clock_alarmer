# Clock Alarmer

A console-based alarm clock application written in Java.

The application allows users to create, view, search, sort, edit, and delete alarms. A background task continuously checks the current time and plays an alarm sound when a scheduled alarm is reached.

## Features

* Add alarms with a specific time and message
* View all scheduled alarms
* Sort alarms by time
* Search for an alarm by time
* Edit existing alarms
* Delete alarms
* Automatically check scheduled alarms in the background
* Play an audio notification when an alarm is triggered
* Automatically remove triggered alarms

## How It Works

Each alarm contains:

* Time in `HH:mm` format
* Message

When an alarm's time matches the current system time, the application:

1. Displays the alarm time and message
2. Plays the configured `alarm.wav` sound
3. Removes the triggered alarm from the list

A background task checks the alarms every minute while the main thread handles user interaction through the console menu.

## Console Menu

When the application starts, the following options are available:

```text
1. Додати будильник
2. Переглянути всі будильники
3. Сортувати будильники за часом
4. Знайти будильник за часом
5. Видалити будильник
6. Редагувати будильник
7. Вийти
```

### Add Alarm

The user enters:

* Alarm time in `HH:mm` format
* Alarm message

Example:

```text
Введіть час будильника (формат HH:mm): 08:30
Введіть повідомлення для будильника: Wake up
```

### View Alarms

Displays all currently scheduled alarms with their position, time, and message.

### Sort Alarms

Alarms are sorted chronologically using the `HH:mm` time format.

### Search Alarm

The user can search for an alarm by entering its exact time.

### Delete Alarm

An alarm can be removed by its position in the current list.

### Edit Alarm

An existing alarm can be replaced with a new time and message.

## Background Alarm Checking

The application uses `ExecutorService` to run a background task independently from the console input loop.

The task:

* Sorts the alarms
* Checks the current time
* Triggers matching alarms
* Waits for 60 seconds
* Repeats the process

This allows the application to monitor alarms while the user continues interacting with the console.

## Sound Playback

When an alarm is triggered, the application loads `alarm.wav` and plays it using the Java Sound API.

The project uses:

* `AudioSystem`
* `AudioInputStream`
* `Clip`
* `ExecutorService`

The sound file is stored in the project as:

```text
src/alarm.wav
```

## Project Structure

```text
clock_alarmer/
│
├── src/
│   ├── Alarm.java
│   ├── AlarmManager.java
│   └── alarm.wav
│
├── .gitignore
└── AlarmManager.iml
```

### `Alarm.java`

Represents a single alarm.

It stores:

* `time`
* `message`

It also provides the name of the sound file used when the alarm is triggered.

### `AlarmManager.java`

Contains the main application logic.

It is responsible for:

* Managing the alarm list
* Displaying the console menu
* Adding alarms
* Viewing alarms
* Sorting alarms
* Searching alarms
* Editing alarms
* Deleting alarms
* Checking the current time
* Playing the alarm sound
* Running the background monitoring task

## Technologies

* Java
* Java Collections Framework
* Java Concurrency API
* Java Sound API
* `SimpleDateFormat`
* Console input/output

## Data Storage

Alarms are stored in an in-memory `ArrayList`.

The application does not use a database or a file-based persistence system for alarm data. Scheduled alarms therefore exist only while the application is running.

## Running the Project

This project is a simple Java application without a Maven or Gradle build configuration.

Open the project in an IDE such as IntelliJ IDEA and run:

```text
AlarmManager.java
```

Make sure the `alarm.wav` file is available to the application as a classpath resource so that the sound can be loaded when an alarm is triggered.

## Project Purpose

This project demonstrates:

* Java classes and objects
* Collections and list manipulation
* Sorting objects by time
* Searching and modifying collection elements
* Console-based user interaction
* Background task execution with `ExecutorService`
* Working with dates and time formatting
* Audio playback with the Java Sound API
* Basic separation between the alarm model and application logic
