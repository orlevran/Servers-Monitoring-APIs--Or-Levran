The project is written in Java Spring Boot. Using MySQL (connected to my private instance)


__If you are using Visual Studio Code, run it with the following command:__

* ./mvnw clean -DskipTests spring-boot:run "-Dspring-boot.run.arguments=--spring.profiles.active=dev"


**Change the value of spring.mail.from in src\main\resources\application.properties**

--------------------------------------------------------------------------------------
Given a curl collection with the needed requests (I recommend starting with request number 17- creating user):

```json
{
	"info": {
		"_postman_id": "5bc8c2e1-33e0-4b43-89fb-d1259a4a70ed",
		"name": "Servers Monitoring API - Full Collection",
		"description": "All endpoints for the servers monitoring project. Set {{serverId}} after creating a server. Adjust {{timestamp}} as needed.",
		"schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json",
		"_exporter_id": "20476221"
	},
	"item": [
		{
			"name": "1) Create Server - HTTP (HEALTHY 2xx)",
			"request": {
				"method": "POST",
				"header": [
					{
						"key": "Content-Type",
						"value": "application/json"
					}
				],
				"body": {
					"mode": "raw",
					"raw": "{\n  \"name\": \"http-ok\",\n  \"protocol\": \"HTTP\",\n  \"url\": \"http://httpstat.us/200\"\n}"
				},
				"url": {
					"raw": "{{baseUrl}}/servers",
					"host": [
						"{{baseUrl}}"
					],
					"path": [
						"servers"
					]
				}
			},
			"response": []
		},
		{
			"name": "2) Create Server - HTTP (UNHEALTHY non-2xx)",
			"request": {
				"method": "POST",
				"header": [
					{
						"key": "Content-Type",
						"value": "application/json"
					}
				],
				"body": {
					"mode": "raw",
					"raw": "{\n  \"name\": \"http-bad\",\n  \"protocol\": \"HTTP\",\n  \"url\": \"http://httpstat.us/503\"\n}"
				},
				"url": {
					"raw": "{{baseUrl}}/servers",
					"host": [
						"{{baseUrl}}"
					],
					"path": [
						"servers"
					]
				}
			},
			"response": []
		},
		{
			"name": "3) Create Server - HTTPS (HEALTHY 2xx)",
			"request": {
				"method": "POST",
				"header": [
					{
						"key": "Content-Type",
						"value": "application/json"
					}
				],
				"body": {
					"mode": "raw",
					"raw": "{\n  \"name\": \"https-ok\",\n  \"protocol\": \"HTTPS\",\n  \"url\": \"https://httpstat.us/200\"\n}"
				},
				"url": {
					"raw": "{{baseUrl}}/servers",
					"host": [
						"{{baseUrl}}"
					],
					"path": [
						"servers"
					]
				}
			},
			"response": []
		},
		{
			"name": "4) Create Server - HTTPS (UNHEALTHY non-2xx)",
			"request": {
				"method": "POST",
				"header": [
					{
						"key": "Content-Type",
						"value": "application/json"
					}
				],
				"body": {
					"mode": "raw",
					"raw": "{\n  \"name\": \"https-bad\",\n  \"protocol\": \"HTTPS\",\n  \"url\": \"https://httpstat.us/503\"\n}"
				},
				"url": {
					"raw": "{{baseUrl}}/servers",
					"host": [
						"{{baseUrl}}"
					],
					"path": [
						"servers"
					]
				}
			},
			"response": []
		},
		{
			"name": "5) Create Server - FTP (HEALTHY connection)",
			"request": {
				"method": "POST",
				"header": [
					{
						"key": "Content-Type",
						"value": "application/json"
					}
				],
				"body": {
					"mode": "raw",
					"raw": "{\n  \"name\": \"ftp-ok\",\n  \"protocol\": \"FTP\",\n  \"host\": \"ftp.dlptest.com\",\n  \"port\": 21,\n  \"username\": \"dlpuser\",\n  \"password\": \"rNrKYTX9g7z3RgJRmxWuGHbeu\"\n}"
				},
				"url": {
					"raw": "{{baseUrl}}/servers",
					"host": [
						"{{baseUrl}}"
					],
					"path": [
						"servers"
					]
				}
			},
			"response": []
		},
		{
			"name": "6) Create Server - FTP (UNHEALTHY wrong creds)",
			"request": {
				"method": "POST",
				"header": [
					{
						"key": "Content-Type",
						"value": "application/json"
					}
				],
				"body": {
					"mode": "raw",
					"raw": "{\n  \"name\": \"ftp-bad\",\n  \"protocol\": \"FTP\",\n  \"host\": \"ftp.dlptest.com\",\n  \"port\": 21,\n  \"username\": \"dlpuser\",\n  \"password\": \"wrongpass\"\n}"
				},
				"url": {
					"raw": "{{baseUrl}}/servers",
					"host": [
						"{{baseUrl}}"
					],
					"path": [
						"servers"
					]
				}
			},
			"response": []
		},
		{
			"name": "7) Create Server - SSH (HEALTHY connection)",
			"request": {
				"method": "POST",
				"header": [
					{
						"key": "Content-Type",
						"value": "application/json"
					}
				],
				"body": {
					"mode": "raw",
					"raw": "{\n  \"name\": \"ssh-ok\",\n  \"protocol\": \"SSH\",\n  \"host\": \"test.rebex.net\",\n  \"port\": 22,\n  \"username\": \"demo\",\n  \"password\": \"password\"\n}"
				},
				"url": {
					"raw": "{{baseUrl}}/servers",
					"host": [
						"{{baseUrl}}"
					],
					"path": [
						"servers"
					]
				}
			},
			"response": []
		},
		{
			"name": "8) Create Server - SSH (UNHEALTHY bad port)",
			"request": {
				"method": "POST",
				"header": [
					{
						"key": "Content-Type",
						"value": "application/json"
					}
				],
				"body": {
					"mode": "raw",
					"raw": "{\n  \"name\": \"ssh-bad\",\n  \"protocol\": \"SSH\",\n  \"host\": \"test.rebex.net\",\n  \"port\": 2222,\n  \"username\": \"demo\",\n  \"password\": \"password\"\n}"
				},
				"url": {
					"raw": "{{baseUrl}}/servers",
					"host": [
						"{{baseUrl}}"
					],
					"path": [
						"servers"
					]
				}
			},
			"response": []
		},
		{
			"name": "9) Edit Server (PUT)",
			"request": {
				"method": "PUT",
				"header": [
					{
						"key": "Content-Type",
						"value": "application/json"
					}
				],
				"body": {
					"mode": "raw",
					"raw": "{\n  \"name\": \"http-ok-renamed\",\n  \"url\": \"http://httpstat.us/204\"\n}"
				},
				"url": {
					"raw": "{{baseUrl}}/servers/{{serverId}}",
					"host": [
						"{{baseUrl}}"
					],
					"path": [
						"servers",
						"{{serverId}}"
					]
				}
			},
			"response": []
		},
		{
			"name": "10) Get Server by ID",
			"request": {
				"method": "GET",
				"header": [],
				"url": {
					"raw": "{{baseUrl}}/servers/{{serverId}}",
					"host": [
						"{{baseUrl}}"
					],
					"path": [
						"servers",
						"{{serverId}}"
					]
				}
			},
			"response": []
		},
		{
			"name": "11) Get All Servers",
			"request": {
				"method": "GET",
				"header": [],
				"url": {
					"raw": "{{baseUrl}}/servers",
					"host": [
						"{{baseUrl}}"
					],
					"path": [
						"servers"
					]
				}
			},
			"response": []
		},
		{
			"name": "12) Requests History (paged)",
			"request": {
				"method": "GET",
				"header": [],
				"url": {
					"raw": "{{baseUrl}}/servers/{{serverId}}/requests?page=0&size=50",
					"host": [
						"{{baseUrl}}"
					],
					"path": [
						"servers",
						"{{serverId}}",
						"requests"
					],
					"query": [
						{
							"key": "page",
							"value": "0"
						},
						{
							"key": "size",
							"value": "50"
						}
					]
				}
			},
			"response": []
		},
		{
			"name": "13) Check Server Now (probe + persist)",
			"request": {
				"method": "GET",
				"header": [],
				"url": {
					"raw": "{{baseUrl}}/servers/{{serverId}}/check",
					"host": [
						"{{baseUrl}}"
					],
					"path": [
						"servers",
						"{{serverId}}",
						"check"
					]
				}
			},
			"response": []
		},
		{
			"name": "14) Healthy At (timestamp seconds, Z)",
			"request": {
				"method": "GET",
				"header": [],
				"url": {
					"raw": "{{baseUrl}}/servers/{{serverId}}/healthy-at?timestamp={{timestamp}}",
					"host": [
						"{{baseUrl}}"
					],
					"path": [
						"servers",
						"{{serverId}}",
						"healthy-at"
					],
					"query": [
						{
							"key": "timestamp",
							"value": "{{timestamp}}",
							"description": "Format yyyy-MM-ddTHH:mm:ssZ"
						}
					]
				}
			},
			"response": []
		},
		{
			"name": "15) Delete Server - success",
			"request": {
				"method": "DELETE",
				"header": [],
				"url": {
					"raw": "{{baseUrl}}/servers/{{serverId}}",
					"host": [
						"{{baseUrl}}"
					],
					"path": [
						"servers",
						"{{serverId}}"
					]
				}
			},
			"response": []
		},
		{
			"name": "16) Delete Server - fail (404)",
			"request": {
				"method": "DELETE",
				"header": [],
				"url": {
					"raw": "{{baseUrl}}/servers/{{badServerId}}",
					"host": [
						"{{baseUrl}}"
					],
					"path": [
						"servers",
						"{{badServerId}}"
					]
				}
			},
			"response": []
		},
		{
			"name": "17) Create User",
			"request": {
				"method": "POST",
				"header": [
					{
						"key": "Content-Type",
						"value": "application/json"
					}
				],
				"body": {
					"mode": "raw",
					"raw": "{\n  \"email\": \"orlevor@gmail.com\"\n}"
				},
				"url": {
					"raw": "{{baseUrl}}/users",
					"host": [
						"{{baseUrl}}"
					],
					"path": [
						"users"
					]
				}
			},
			"response": []
		},
		{
			"name": "18) Get All Users",
			"request": {
				"method": "GET",
				"header": [],
				"url": {
					"raw": "{{baseUrl}}/users",
					"host": [
						"{{baseUrl}}"
					],
					"path": [
						"users"
					]
				}
			},
			"response": []
		},
		{
			"name": "19) Ping",
			"request": {
				"method": "GET",
				"header": [],
				"url": {
					"raw": "{{baseUrl}}/ping",
					"host": [
						"{{baseUrl}}"
					],
					"path": [
						"ping"
					]
				}
			},
			"response": []
		}
	],
	"variable": [
		{
			"key": "baseUrl",
			"value": "http://localhost:8080/api/v1"
		},
		{
			"key": "serverId",
			"value": "1"
		},
		{
			"key": "badServerId",
			"value": "999999"
		},
		{
			"key": "timestamp",
			"value": "2025-10-21T00:10:00Z"
		}
	]
}
