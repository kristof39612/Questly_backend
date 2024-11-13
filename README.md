# Endpoints

## Test

### GET Test
GET<br>
/api/v1/test<br><br>
ResponseBody:<br>
```
Get works
```
### POST Test
POST<br>
/api/v1/test<br><br>
RequestBody:<br>
```json
{
  "message" : "POST Works"
}
```
ResponseBody:<br>
```
POST Works
```

## Authentication

### Register
POST<br>
/auth/register<br><br>
RequestBody:<br>
```json
{
  "email": "asd@asd.com",
  "password": "asd"
}
```
ResponseBody:<br>
```json
{
  "token": "token",
  "errorMessage": null
}
```
### Login
POST<br>
/auth/login<br><br>
RequestBody:<br>
```json
{
  "email": "asd@asd.com",
  "password": "asd"
}
```
ResponseBody:<br>
```json
{
  "token": "token",
  "errorMessage": null
}
```
## TaskPoints

### Get All TaskPoints
GET<br>
/taskpoint<br><br>
ResponseBody:<br>
```json
[
  {
    "id": 1,
    "title": "TextPromptTask1",
    "task": {
      "type": "TextPromptTask",
      "id": 10,
      "pointsForCompletion": 25,
      "question": "What is the capital of Hungary?",
      "answer": "Budapest"
    },
    "status": "APPROVED",
    "location": {
      "latitude": 47.4977,
      "longitude": 19.0507
    },
    "authorUserId": "1",
    "rating": 3.5
  },
  {
    "id": 2,
    "title": "GotoPointTask2",
    "task": {
      "type": "GoToPointTask",
      "id": 11,
      "pointsForCompletion": 50,
      "where": {
        "latitude": 47.4977,
        "longitude": 19.0507
      }
    },
    "status": "PENDING",
    "location": {
      "latitude": 47.4977,
      "longitude": 19.0513
    },
    "authorUserId": "5",
    "rating": 4.0
  }
]
```
### Get TaskPoint by ID
GET<br>
/taskpoint/{id}<br><br>
ResponseBody:<br>
```json
{
  "id": 1,
  "title": "TextPromptTask1",
  "task": {
    "type": "TextPromptTask",
    "id": 10,
    "pointsForCompletion": 25,
    "question": "What is the capital of Hungary?",
    "answer": "Budapest"
  },
  "status": "APPROVED",
  "location": {
    "latitude": 47.4977,
    "longitude": 19.0507
  },
  "authorUserId": "1",
  "rating": 3.5
}
```
### Add Taskpoint
POST<br>
/taskpoint<br><br>
RequestBody:<br>
```json
{
  "id": 1,
  "title": "TextPromptTask1",
  "task": {
    "type": "TextPromptTask",
    "id": 10,
    "pointsForCompletion": 25,
    "question": "What is the capital of Hungary?",
    "answer": "Budapest"
  },
  "status": "APPROVED",
  "location": {
    "latitude": 47.4977,
    "longitude": 19.0507
  },
  "authorUserId": "1",
  "rating": 3.5
}
```
ResponseBody:<br>
```json
{
  "id": 1,
  "title": "TextPromptTask1",
  "task": {
    "type": "TextPromptTask",
    "id": 10,
    "pointsForCompletion": 25,
    "question": "What is the capital of Hungary?",
    "answer": "Budapest"
  },
  "status": "APPROVED",
  "location": {
    "latitude": 47.4977,
    "longitude": 19.0507
  },
  "authorUserId": "1",
  "rating": 3.5
}
```
### Approve Taskpoint (ADMIN only)
PATCH<br>
/taskpoint/{id}/approve<br><br>
ResponseBody:<br>
```json
{
  "id": 1,
  "title": "TextPromptTask1",
  "task": {
    "type": "TextPromptTask",
    "id": 10,
    "pointsForCompletion": 25,
    "question": "What is the capital of Hungary?",
    "answer": "Budapest"
  },
  "status": "APPROVED",
  "location": {
    "latitude": 47.4977,
    "longitude": 19.0507
  },
  "authorUserId": "1",
  "rating": 3.5
}
```

### Reject Taskpoint (ADMIN only)
PATCH<br>
/taskpoint/{id}/reject<br><br>
ResponseBody:<br>
```json
{
  "id": 1,
  "title": "TextPromptTask1",
  "task": {
    "type": "TextPromptTask",
    "id": 10,
    "pointsForCompletion": 25,
    "question": "What is the capital of Hungary?",
    "answer": "Budapest"
  },
  "status": "REJECTED",
  "location": {
    "latitude": 47.4977,
    "longitude": 19.0507
  },
  "authorUserId": "1",
  "rating": 3.5
}
```
### Delete Taskpoint (ADMIN only)
DELETE<br>
/taskpoint/{id}<br><br>

## User actions

### Get current TaskPoint ID
GET<br>
/user/currentTask<br><br>
ResponseBody:<br>
```json
{
  "taskPointId": 10
}
```
### Start selected TaskPoint
PATCH<br>
/user/startTask<br><br>
RequestBody:<br>
```json
{
  "taskPointId": 10
}
```
### Cancel current TaskPoint
PATCH<br>
/user/cancelTask<br><br>

### Complete current TaskPoint
POST<br>
/user/completeTask<br><br>
RequestBody:<br>
```
{
  "givenRating": "4",
  "photo": MultiPart File
}
```
ResponseBody:<br>
```json
{
    "id": 8,
    "visitedPointId": 10,
    "visitDate": "2024-11-13T20:01:49.8319381",
    "userId": 2,
    "photoId": 19,
    "givenRating": 4
}
```
### Get Photo by ID
GET<br>
/user/photo/{id}<br><br>
ResponseBody:<br>
```
Photo in ByteStream (currently Content-Type header is set to image/png)
```
### Get all LogEntries for current User
GET<br>
/user/getLogEntries<br><br>
ResponseBody:<br>
```json
[
  {
    "id": 1,
    "visitedPointId": 10,
    "visitDate": "2024-11-13T19:03:32.127082",
    "userId": 2,
    "photoId": 12,
    "givenRating": 3
  },
  {
    "id": 6,
    "visitedPointId": 11,
    "visitDate": "2024-11-13T20:01:49.831938",
    "userId": 2,
    "photoId": 19,
    "givenRating": 3
  }
]
```
## Leaderboard

### Get points for current user
GET<br>
/user/points<br><br>
ResponseBody:<br>
```json
{
  "username": "asd@asd.com",
  "points": 75
}
```
### Get Leaderboard
GET<br>
/leaderboard<br><br>
ResponseBody:<br>
```json
[
  {
    "username": "admin@asd.com",
    "points": 175
  },
  {
    "username": "asdasd@asd.com",
    "points": 75
  },
  {
    "username": "asd@asd.com",
    "points": 0
  }
]
```
