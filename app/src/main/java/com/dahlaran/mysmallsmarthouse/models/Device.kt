package com.dahlaran.mysmallsmarthouse.models

abstract class Device(open val id: Int, open val name: String, open val productType: ProductType){
    override fun equals(other: Any?): Boolean {
        return (other != null && other is Device &&
            other.id == id &&
            other.name == name &&
            other.productType == productType)
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + productType.hashCode()
        return result
    }

}
