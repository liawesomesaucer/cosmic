import os
import tornado.web

from sqlalchemy.orm import scoped_session, sessionmaker
from models import User, testUser, TutorSession
from sqlalchemy import create_engine, MetaData
from sqlalchemy.orm import sessionmaker

DB_URL = "postgres://nxuzisdyscgaic:XcSLKwBZ684bt6EMSml59pPcgm@ec2-54-163-235-165.compute-1.amazonaws.com:5432/d60q0hmibvq90e"
engine = create_engine(DB_URL)

Session = sessionmaker(bind=engine)
session = Session()

metadata = MetaData()
metadata.create_all(bind=engine)
from db import *

import json

# Default handler
class BaseHandler(tornado.web.RequestHandler):
	def db(self):
		return self.application.db

	# Get the current user from the db
	def get_current_user(self):
		user_id = self.get_secure_cookie("user")
		if not user_id: 
			return None
		return self.db.query(User).get(user_id)

from handlers.create_user import CreateUserHandler

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
			(r"/json_test",		JsonTestHandler),
			(r"/create_user",	CreateUserHandler)
		]

		tornado.web.Application.__init__(self, handlers)