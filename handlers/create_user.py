import tornado.web
import tornado.escape
from db import session
from models import User
from application import BaseHandler

# Creating new users
class CreateUserHandler( BaseHandler ):

	def post( self ):

		try:
			data = tornado.escape.json_decode(self.request.body)

			if not data:
				self.write("no data recieved")

			print("AFSDAFDASSDFADFSSD>>>%s" % data)

			new_user = User( 	data["email"],
								data["password"],
								data["name"] )

			# Add new user to db
			session.add( new_user )
			session.commit()

		except:
			self.write( "Something horrible happened" )
			raise
			print( "CreateUserHandler failed")
