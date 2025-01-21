
const User = require('../model/User');
const jwt = require("jsonwebtoken");
const dotenv = require('dotenv');
const bcrypt = require('bcrypt');
dotenv.config()
let success = false;


const resetPassword = async (req, res) => {
    const { id, currentPassword, newPassword } = req.body;
    const findUser = await User.findById(id)
    if (findUser) {
      try {
        const oldPassword = findUser.password;
        const passwordCompare = await bcrypt.compare(currentPassword, oldPassword)
        if (!passwordCompare) {
          return res.status(400).send("Please enter correct password");
        }
        else {
          const salt = await bcrypt.genSalt(10)
          const hashedPass = await bcrypt.hash(newPassword, salt)
          const isSuccess = await User.findByIdAndUpdate(findUser, {
            $set: {
              password: hashedPass
            }
          })
          if (isSuccess) {
            success=true;
            res.send("reset successfully")
          }
        }
      } catch (error) {
        return res.status(400).send("Something went wrong")
  
      }
    }
    else {
      return res.status(400).json("User not found");
    }
  }


  module.exports = {resetPassword}
  