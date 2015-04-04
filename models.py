from sqlalchemy import create_engine
from sqlalchemy import Column, Integer, String, Float, Boolean, DateTime
from utils import *
import utils, datetime

from sqlalchemy.ext.declarative import declarative_base

Base = declarative_base()

class User( Base ):
	__tablename__ = 'users'

	# Total of 11 fields

	# Default, important user fields
	email = Column(String(100), primary_key=True)
	name = Column(String(50))
	password = Column(String(100))

	# Personal info fields
	bio = Column(String(200))	# Also where tutors store credentials
	image = Column(String(200))	# Uri for image

	# If this guy is a tutor
	tutor = Column(Boolean())
	tutor_availability = Column(Boolean())	# If tutor is available for tutoring
	tutor_rating = Column(Float())
	tutor_fields = Column(String(5000))
	tutor_price = Column(Float())	# Tutor's desired price per hour

	# Optional fields
	location = Column(String(20))	# Location, not sure if necessary

	def __init__ (	self, email, password, name, bio=None, image=None, tutor=False,
					tutor_availability=False, tutor_rating=0.0, tutor_fields="",
					tutor_price=0.0, location="" ):

		self.email = email
		self.password = password
		self.name = name
		self.bio = bio
		self.image = image
		self.tutor = tutor
		self.tutor_availability = tutor_availability
		self.tutor_rating = tutor_rating
		self.tutor_fields = tutor_fields
		self.tutor_price = tutor_price
		self.location = location

	def __repr__ ( self ):
		if self.tutor:			
			return "<Tutor %s>" % name
		return "<User %s>" % name

# For the tutor session
class TutorSession( Base ):
	__tablename__ = 'tutor_session'

	startTime = Column( DateTime, 
						default=datetime.datetime.utcnow, 
						primary_key=True)
	endTime = Column( DateTime )
	
	tutor = Column( String(100) )

	def __init__ (self, startTime, endTime, tutor):
		self.startTime = startTime
		self.endTime = endTime
		self.tutor = tutor

	def end_session( self ):
		return





# class Review( Base ):			# Optional for review implementation


testUser = {
	"email" : "mwang@wang",
	"name" : "Michael Wang",
	"password" : "123",
	"bio" : "Stuff stuff stuff",
	"image" : "http://upload.wikimedia.org/wikipedia/commons/thumb/8/85/Smiley.svg/2000px-Smiley.svg.png",
	"tutor" : False,
	"tutor_availability": False,
	"tutor_rating": 3.5,
	"tutor_fields": "English Chinese Japanese Mandarin ETC",
	"tutor_price": 25,
	"location" : ""
}	# testUser, total of 11 fields
