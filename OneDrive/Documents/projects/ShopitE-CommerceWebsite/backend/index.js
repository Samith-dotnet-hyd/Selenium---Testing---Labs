const express = require("express");
const cors = require("cors")
const connectToMongo = require("./config/db")
const auth = require("./routes/auth")
const product = require("./routes/product")
const cart = require("./routes/cart")
const wishlist = require("./routes/wishlist")
const review = require("./routes/review")
const AdminRoute = require("./routes/Admin/AdminAuth")
const paymentRoute = require("./routes/paymentRoute")
const forgotPassword = require("./routes/forgotPassword")

const app = express()


app.get("/", (req, res)=>{
    res.send('Hello World')
})

app.use(express.urlencoded({extended:true}))
app.use(express.json());

app.use(cors())

app.use("/api/auth", auth)

app.use('/api/product', product)

app.use('/api/cart', cart)

app.use('/api/wishlist', wishlist)

app.use('/api/review', review)

app.use('/api/admin', AdminRoute)

app.use('/api', paymentRoute)

app.use('/api/password', forgotPassword)


app.listen(7300, async()=>{
    try {
        await connectToMongo()
        console.log('Server is running on port 7300')
    } catch (error) {
        console.log(error)
    }
    
})