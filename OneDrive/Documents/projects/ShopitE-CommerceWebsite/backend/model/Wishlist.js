const mongoose = require("mongoose")

const WishlistSchema = new mongoose.Schema({
    user: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'user'
    },
    productId: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'product'
    },
    date: {
        type: Date,
        default: Date.now
    }
})

module.exports = mongoose.model("wishlist", WishlistSchema)
