const User = require("../model/User");
const Cart = require("../model/Cart");
const Wishlist = require("../model/Wishlist");
const Review = require("../model/Review");
const Payment = require("../model/Payment")
const Product = require("../model/Product")


const chartData = async (req, res) => {
    try {
        const cart = await Cart.find().populate("productId");
        const wishlist = await Wishlist.find().populate("productId");

        const payment = await Payment.find();
        const product = await Product.find();
        const review = await Review.find();
        res.send({ review, product, payment, wishlist, cart });
    } catch (error) {
        res.send(error);

    }
}
module.exports = { chartData }