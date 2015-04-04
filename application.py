import os
import tornado.web

from sqlalchemy.orm import scoped_session, sessionmaker

import json

testUser = {
	"name" : "Michael Wang",
	"email" : "mwang@wang",
	"password" : "123",
	"description" : "Stuff stuff stuff",
	"profile_pic" : "http://upload.wikimedia.org/wikipedia/commons/thumb/8/85/Smiley.svg/2000px-Smiley.svg.png",
	"interests" : "Eating Badminton Gymming",
	"classes" : "CSE20 CSE30",
	"yesUsers" : "",
	"noUsers" : "",
	"rating" : "4.9",
	"rating_times" : "10",
	"location" : "temporaryvariable"
}	# testUser

# Default handler
class BaseHandler(tornado.web.RequestHandler):
	def set_default_headers(self):
		def db(self):
			return self.application.db

		# Get the current user from the db
		def get_current_user(self):
			user_id = self.get_secure_cookie("user")
			if not user_id: 
				return None
			return self.db.query(User).get(user_id)

# Hello World
class HelloHandler(BaseHandler):

	def get(self):
		try:
			self.write(json.dumps(testUser))
		except:
			print("Hello failed")
			raise

# Testing JSON Input
class JsonTestHandler(BaseHandler):
	def post(self):
		try:
			data = tornado.escape.json_decode(self.request.body)
			print(data)
			self.write(data)
		except:
			raise
			self.write("FAILED")

class Application(tornado.web.Application):
	def __init__(self):
		
		handlers = [
			(r"/", 				HelloHandler),
			(r"/json_test",		JsonTestHandler)
		]

		#self.db = scoped_session(sessionmaker(bind=engine))

		tornado.web.Application.__init__(self, handlers)