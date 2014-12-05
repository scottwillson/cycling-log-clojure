#! /usr/bin/env ruby

require "mysql2"
mysql = Mysql2::Client.new(:host => "localhost", :username => "root", :database => "cycling_log")

require "mongo"
mongo  = Mongo::MongoClient.new #('localhost', 3002)
db     = mongo['cycling-log']

coll   = db['workouts']
coll.remove
mysql.query(
  "select workouts.*, equipment.name as equipment_name, routes.name as route_name,
    users.firstName, users.lastName, users.username
  from workouts
  inner join users on users.id = workouts.userId
  left outer join equipment on equipment.id = workouts.equipmentId
  left outer join routes on routes.id = workouts.route_id
  where public_notes is not null or public_notes != '' or duration > 0 or distance > 0").each do |r|
  putc "."
  coll.insert({
    id: r["id"],
    date: r["date"].to_s,
    user_id: r["userId"],
    activity: r["activity"],
    distance: r["distance"],
    minutes: r["duration"],
    first_name: r["firstName"],
    last_name: r["lastName"],
    name: "#{r["firstName"]} #{r["lastName"]}".strip,
    username: r["username"],
    public_notes: r["public_notes"],
    notes: r["notes"],
    speed: r["speed"],
    intensity: r["intensity"],
    morale: r["morale"],
    life: r["life"],
    weather: r["weather"],
    weight: r["weight"],
    work: r["work"],
    race: r["race"],
    focus: r["focus"],
    week_id: r["weekId"],
    equipment_id: r["equipmentId"],
    equipment_name: r["equipment_name"],
    average_heart_rate: r["average_heart_rate"],
    maximum_heart_rate: r["maximum_heart_rate"],
    route_id: r["route_id"],
    route_name: r["route_name"]
  })
end
puts

coll = db["users"]
coll.remove
mysql.query("select * from users").each do |r|
  coll.insert({
    id: r["id"],
    username: r["username"],
    password: r["password"],
    email: r["email"],
    firstName: r["firstName"],
    lastName: r["lastname"]
  })
end

coll = db["weeks"]
coll.remove
mysql.query("select * from weeks").each do |r|
  coll.insert({
    id: r["id"],
    start_date: r["startDate"].to_s,
    end_date: r["endDate"].to_s,
    notes: r["notes"],
    public_notes: r["public_notes"],
    user_id: r["userId"]
  })
end
