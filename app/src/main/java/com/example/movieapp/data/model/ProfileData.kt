package com.example.movieapp.data.model

import android.os.Parcel
import android.os.Parcelable

data class ProfileData(
    val userId: Int?,
    val username: String?,
    val email: String?,
    val password: String?,
    val role: String?,
    val profileUrl: String?,
    val bookmarks: List<Bookmark>?
)

data class Bookmark(
    val id: Int,
    val date: String,
    val movie: MovieModel
)

data class MovieModel(
    val id: Int,
    val title: String,
    val description: String,
    val releaseDate: String,
    val posterUrl: String,
    val rating: String,
    val movieUrl: String,
    val actorName: String?,
    val movieType: MovieType,
    val reviews: List<Review>,
    val banner: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString(),
        parcel.readParcelable(MovieType::class.java.classLoader) ?: MovieType(0, "", "", null),
        parcel.createTypedArrayList(Review.CREATOR) ?: emptyList(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(releaseDate)
        parcel.writeString(posterUrl)
        parcel.writeString(rating)
        parcel.writeString(movieUrl)
        parcel.writeString(actorName)
        parcel.writeParcelable(movieType, flags)
        parcel.writeTypedList(reviews)
        parcel.writeString(banner)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MovieModel> {
        override fun createFromParcel(parcel: Parcel): MovieModel {
            return MovieModel(parcel)
        }

        override fun newArray(size: Int): Array<MovieModel?> {
            return arrayOfNulls(size)
        }
    }
}

data class MovieType(
    val id: Int,
    val name: String,
    val description: String,
    val imageUrl: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeString(imageUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MovieType> {
        override fun createFromParcel(parcel: Parcel): MovieType {
            return MovieType(parcel)
        }

        override fun newArray(size: Int): Array<MovieType?> {
            return arrayOfNulls(size)
        }
    }
}

data class Review(
    val id: Int,
    val review: String,
    val rating: Int,
    val comment: String,
    val reviewDate: String,
    val movieId: Int,
    val userId: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(review)
        parcel.writeInt(rating)
        parcel.writeString(comment)
        parcel.writeString(reviewDate)
        parcel.writeInt(movieId)
        parcel.writeInt(userId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Review> {
        override fun createFromParcel(parcel: Parcel): Review {
            return Review(parcel)
        }

        override fun newArray(size: Int): Array<Review?> {
            return arrayOfNulls(size)
        }
    }
}