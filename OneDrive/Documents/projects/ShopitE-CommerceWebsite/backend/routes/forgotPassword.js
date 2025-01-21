const express = require('express');
const router = express.Router();
const authUser = require('../middleware/apiUser');

const dotenv = require('dotenv');
const { resetPassword } = require('../controller/forgotPasswordController');
dotenv.config()

// router.post('/forgot-password', sendEmailLink);
// router.post('/forgot-password/:id/:token',setNewPassword );
router.post('/reset', authUser, resetPassword);


module.exports = router