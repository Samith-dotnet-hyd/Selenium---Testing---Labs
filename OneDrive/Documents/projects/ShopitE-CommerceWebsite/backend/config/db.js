const mongoose = require("mongoose");
const dotenv = require("dotenv");
dotenv.config()

const connectToMongo = async()=>{
    try {
        await mongoose.connect(process.env.mongoDB)
        console.log("connected")
    } catch (error) {
        console.log(error)
    }
}

module.exports = connectToMongo