# chat-bot

Chat-bot for [Development of scalable profuction-grade information and communication systems](https://www.fer.unizg.hr/en/course/dospiacs)

Swagger endpoint [/api/v1/swagger-ui/index.html](https://chatbot.commanderkowalski.uk/api/v1/swagger-ui/index.html)    ( [local](http://localhost:8080/api/v1/swagger-ui.index.html) )

Messaging can be done with /messages with 2 users or /chatbot where user is comunicating with open ai chatbot.

When using regular messaging both participants need to be provided while chatting with a bot is done only with "sender" field.

Examples can be found in http-requests/chatbot.http


Get your own openai api key [here](https://platform.openai.com/account/api-keys) and set environment variable `OPENAI_API_KEY`
