const express = require('express');
const router = express.Router()
const Payment = require('../model/Payment')
const User = require('../model/User')
const authUser = require('../middleware/apiUser')
const dotenv = require('dotenv');
dotenv.config()


router.get('/getPreviousOrders', authUser, async (req, res) => {
  try {
    const data = await Payment.find({ user: req.user.id }).sort({ createdAt: -1 })
    res.send(data)
  }
  catch (error) {
    res.status(500).send("Something went wrong")
  }
})

module.exports = router