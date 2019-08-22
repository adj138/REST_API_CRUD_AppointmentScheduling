Plan for the API:

* For each doctor we would initially like to store the following:
  * id
	* name
	* locations - represented as a collection of address strings
	* schedule - weekly schedule indicating the hours they are available each day of the week
      DB Setup:
        Provider Table [id, name, inactive_flag]
        Location Table [id, name, address 1, address 2, city, state, zip1, zip2, inactive_flag]
        Weekly Schedule Table [schedule_id, provider_id, location_id, day_of_week, start_time, end_time, block_flag, delete_flag]
        Provider Schedule Table [even_id, provider_id, location_id, schedule_id, date, start_time, end_time, time_zone, visit_type_id, visit_status_id, delete_flag]
        Appointment Status Table [appt_sts_id, name, inactive_flag]
        Appointment Table
* CRUD operations for doctors [/doctorbyid/{id}]
* Ability to book an appointment with a doctor (a tuple of (doctor, location, time)) [/CreateAnAppointment]
* Ability to get all appointments for a doctor [/AppointmentsByDoctor?docid=1]
* Ability to cancel an appointment with a doctor [/UpdateAppointmentByID]

Expectations/assumptions:

* The API will be internally-facing and used by other applications/services that we trust [VPC endpoint]
* The API will be single-tenant (it only contains data for a single hospital) [that's fine can use GUID]
* A doctor is available at any of their locations for any of their available times
* A doctor can only have one appointment at a time
* A doctor can travel instantaneously between locations
* No UI/front-end is expected

To run the JAVA API:
Install Apache Tomcat
1. Navigate to folder where the JAR is downloaded
2. Run: java -jar AppointmentSchedulingAPI.jar
http://localhost:8080/Appointments

Below are the script to create the Database and send API Requests.

I have also included sample responses.

CREATE DATABASE db1

USE db1

CREATE TABLE DOCTORS
(
	ID INT NOT NULL AUTO_INCREMENT,
    FIRST_NAME VARCHAR(40) NOT NULL,
    LAST_NAME VARCHAR(40) NOT NULL,
    PRIMARY KEY (ID)
)

CREATE TABLE DOCTOR_SCHEDULE
(
	ID INT NOT NULL AUTO_INCREMENT,
    DOCTOR_ID INT,
    WEEK_NO INT NOT NULL,
	START_TIME time NOT NULL,
	END_TIME time NOT NULL,
    LOCATION VARCHAR(100) NOT NULL,
    PRIMARY KEY (ID),
    FOREIGN KEY (DOCTOR_ID) REFERENCES DOCTORS(ID) on DELETE CASCADE
)

CREATE TABLE APPOINTMENTS
(
	ID INT NOT NULL AUTO_INCREMENT,
    DOCTOR_ID INT,
    DOCTOR_SCHEDULE_ID INT,
    PATIENT_ID INT,
    LOCATION VARCHAR(100),
    APPOINTMENT_DATE DATE,
    START_TIME TIME,
    END_TIME TIME,
    CANCEL_FLAG TINYINT(1),
    CREATED TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (ID),
	FOREIGN KEY (DOCTOR_ID) REFERENCES DOCTORS(ID) on DELETE CASCADE,
    FOREIGN KEY (DOCTOR_SCHEDULE_ID) REFERENCES DOCTORSCHEDULE(ID)  on DELETE CASCADE
)
------------------------------------------------------------------------------------------------------------------------------------------------
insert into doctors values(1,'Sam','Willis');
insert into doctors values(2, 'Mickey', 'Mouse');
insert into doctors values(3, 'Little', 'Donkey');
------------------------------------------------------------------------------------------------------------------------------------------------
POST REQUEST: http://localhost:8080/PracticeManagment/CreateDoctureSchedule
{
	"doctorId":2,
	"weekNo":21,
	"startTime": "12:18:00",
	"endTime": "14:18:00",
	"location":"123 Infinity Lane, Waltham, MA 02123"
}

Response: {
    "id": 1,
    "doctor": {
        "id": 2,
        "firstName": "Mickey",
        "lastName": "Mouse"
    },
    "weekNo": 21,
    "startTime": "12:18:00",
    "endTime": "14:18:00",
    "location": "123 Infinity Lane, Waltham, MA 02123"
}

Create more Doctor Schedule------------------------------------------------------------------------------------------------------------------------------------------------
{
	"doctorId":1,
	"weekNo":21,
	"startTime": "08:00:00",
	"endTime": "12:00:00",
	"location":"123 Infinity Lane, Waltham, MA 02123"
}

{
	"doctorId":1,
	"weekNo":21,
	"startTime": "13:00:00",
	"endTime": "17:00:00",
	"location":"123 Infinity Lane, Waltham, MA 02123"
}



Create Appointments------------------------------------------------------------------------------------------------------------------------------------------------
POST: http://localhost:8080/PracticeManagment/CreateAppointment
{
	"doctorId":1,
	"doctorScheduleId":3,
	"patientId":1,
	"appointmentDate": "2019-12-01",
	"startTime": "13:00:00",
	"endTime": "17:00:00"
}

Response:
{
    "id": 1,
    "doctor": {
        "id": 1,
        "firstName": "Sam",
        "lastName": "Willis"
    },
    "doctorSchedule": {
        "id": 3,
        "doctor": {
            "id": 1,
            "firstName": "Sam",
            "lastName": "Willis"
        },
        "weekNo": 21,
        "startTime": "13:00:00",
        "endTime": "17:00:00",
        "location": "123 Infinity Lane, Waltham, MA 02123"
    },
    "patientId": 1,
    "location": "123 Infinity Lane, Waltham, MA 02123",
    "appointmentDate": "2019-11-30",
    "startTime": "13:00:00",
    "endTime": "17:00:00",
    "cancelFlag": null
}

MORE Appointments:
{
	"doctorId":1,
	"doctorScheduleId":3,
	"patientId":4,
	"appointmentDate": "2019-08-10",
	"startTime": "08:00:00",
	"endTime": "08:30:00"
}

{
	"doctorId":1,
	"doctorScheduleId":2,
	"patientId":3,
	"appointmentDate": "2019-08-10",
	"startTime": "09:00:00",
	"endTime": "10:00:00"
}
{
	"doctorId":1,
	"doctorScheduleId":2,
	"patientId":2,
	"appointmentDate": "2019-08-10",
	"startTime": "08:30:00",
	"endTime": "09:00:00"
}

{
	"doctorId":2,
	"doctorScheduleId":1,
	"patientId":9,
	"appointmentDate": "2019-08-02",
	"startTime": "08:30:00",
	"endTime": "09:00:00"
}


Check-In Patient for their Appointment------------------------------------------------------------------------------------------------------------------------------------------------
PUT: http://localhost:8080/PracticeManagment/UpdateAppointmentByID

{
	"id":5,
	"cancelFlag":true
}

Response:
{
    "id": 5,
    "doctor": {
        "id": 2,
        "firstName": "Mickey",
        "lastName": "Mouse"
    },
    "doctorSchedule": {
        "id": 1,
        "doctor": {
            "id": 2,
            "firstName": "Mickey",
            "lastName": "Mouse"
        },
        "weekNo": 21,
        "startTime": "12:18:00",
        "endTime": "14:18:00",
        "location": "123 Infinity Lane, Waltham, MA 02123"
    },
    "patientId": 9,
    "location": "123 Infinity Lane, Waltham, MA 02123",
    "appointmentDate": "2019-08-01",
    "startTime": "08:30:00",
    "endTime": "09:00:00",
    "cancelFlag": true
}


Get Appointments by Doctor------------------------------------------------------------------------------------------------------------------------------------------------
GET: http://localhost:8080/PracticeManagment/AppointmentsByDoctor?doctorIdParam=1

Response:
[
    {
        "id": 1,
        "doctor": {
            "id": 1,
            "firstName": "Sam",
            "lastName": "Willis"
        },
        "doctorSchedule": {
            "id": 3,
            "doctor": {
                "id": 1,
                "firstName": "Sam",
                "lastName": "Willis"
            },
            "weekNo": 21,
            "startTime": "13:00:00",
            "endTime": "17:00:00",
            "location": "123 Infinity Lane, Waltham, MA 02123"
        },
        "patientId": 1,
        "location": "123 Infinity Lane, Waltham, MA 02123",
        "appointmentDate": "2019-11-30",
        "startTime": "13:00:00",
        "endTime": "17:00:00",
        "cancelFlag": null
    },
    {
        "id": 2,
        "doctor": {
            "id": 1,
            "firstName": "Sam",
            "lastName": "Willis"
        },
        "doctorSchedule": {
            "id": 3,
            "doctor": {
                "id": 1,
                "firstName": "Sam",
                "lastName": "Willis"
            },
            "weekNo": 21,
            "startTime": "13:00:00",
            "endTime": "17:00:00",
            "location": "123 Infinity Lane, Waltham, MA 02123"
        },
        "patientId": 4,
        "location": "123 Infinity Lane, Waltham, MA 02123",
        "appointmentDate": "2019-08-09",
        "startTime": "08:00:00",
        "endTime": "08:30:00",
        "cancelFlag": null
    },
    {
        "id": 3,
        "doctor": {
            "id": 1,
            "firstName": "Sam",
            "lastName": "Willis"
        },
        "doctorSchedule": {
            "id": 2,
            "doctor": {
                "id": 1,
                "firstName": "Sam",
                "lastName": "Willis"
            },
            "weekNo": 21,
            "startTime": "08:00:00",
            "endTime": "12:00:00",
            "location": "123 Infinity Lane, Waltham, MA 02123"
        },
        "patientId": 2,
        "location": "123 Infinity Lane, Waltham, MA 02123",
        "appointmentDate": "2019-08-09",
        "startTime": "08:30:00",
        "endTime": "09:00:00",
        "cancelFlag": null
    },
    {
        "id": 4,
        "doctor": {
            "id": 1,
            "firstName": "Sam",
            "lastName": "Willis"
        },
        "doctorSchedule": {
            "id": 2,
            "doctor": {
                "id": 1,
                "firstName": "Sam",
                "lastName": "Willis"
            },
            "weekNo": 21,
            "startTime": "08:00:00",
            "endTime": "12:00:00",
            "location": "123 Infinity Lane, Waltham, MA 02123"
        },
        "patientId": 3,
        "location": "123 Infinity Lane, Waltham, MA 02123",
        "appointmentDate": "2019-08-09",
        "startTime": "09:00:00",
        "endTime": "10:00:00",
        "cancelFlag": null
    }
]
