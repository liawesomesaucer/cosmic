from sqlalchemy import create_engine, MetaData
from sqlalchemy.orm import sessionmaker
HEROKU_POSTGRESQL_CRIMSON_URL= "postgres://svzgijjedzoxgo:lqiOequvQPlltINbMVmdLKKf1e@ec2-54-163-235-165.compute-1.amazonaws.com:5432/dct7rhlbci5ha1"
DB_URL = "postgres://nxuzisdyscgaic:XcSLKwBZ684bt6EMSml59pPcgm@ec2-54-163-235-165.compute-1.amazonaws.com:5432/d60q0hmibvq90e"

engine = create_engine(HEROKU_POSTGRESQL_CRIMSON_URL, echo=True)

Session = sessionmaker(bind=engine)
session = Session()

meta = MetaData()
meta.create_all(engine)
