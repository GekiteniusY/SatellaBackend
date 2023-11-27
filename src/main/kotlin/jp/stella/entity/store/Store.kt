package jp.stella.entity.store

import arrow.core.*
import jp.stella.ValidationError


/**
 * Web API で利用するデータモデル
 *
 * Repository によるDB とのやりとり、Serviceを介したデータの受け渡し、Controller のレスポンスに利用する
 *
 * @property storeId ストアID
 * @property storeName 店名
 * @property address 住所
 */
data class Store(val storeId: String, val storeName: String,val storeAddress: String)

fun createStore(storeId: String, storeName: String, storeAddress: String): ValidatedNel<ValidationError, Store> {
//    return StoreId.create(storeId).zip(StoreName.create(storeName)).zip(StoreAddress.create(storeAddress)){
//        validatedStoreId, validatedStoreName, validatedStoreAddress -> Store(validatedStoreId, validatedStoreName, validatedStoreAddress)
//    }
    return TODO("実装途中")
}

class StoreId private constructor(val value: String){
    companion object {
        /**
         * ストアIDオブジェクトのファクトリ
         *
         * @param storeId ストアID
         * @rerun 失敗：バリデーションエラー、成功：ストアIDオブジェクト
         */
        fun create(storeId: String): Either<ValidationError, StoreId>{
            // TODO: バリデーションを実装、コメントを修正
            if (storeId.isEmpty() || storeId.isBlank()){
                return ValidationError("ストアIDが条件を満たしていません").left()
            }
            return StoreId(storeId).right()
        }
    }
}


class StoreName private constructor(val value: String){
    companion object {
        /**
         * ストア名オブジェクトのファクトリ
         *　
         * @param storeName ストア名
         * @return 失敗：バリデーションエラー、成功：ストア名オブジェクト
         */
        fun create(storeName: String): Either<ValidationError, StoreName>{
            // TODO: バリデーションを実装、コメントを修正
            if (storeName.isEmpty() || storeName.isBlank()){
                return ValidationError("ストア名が条件を満たしていません").left()
            }
            return StoreName(storeName).right()
        }
    }
}

class StoreAddress private constructor(val value: String) {
    companion object {
        /**
         * ストア住所オブジェクトのファクトリ
         *
         * @param storeAddress ストア住所
         * @return 失敗：バリデーションエラー、成功：ストア住所オブジェクト
         */
        fun create(storeAddress: String): Either<ValidationError, StoreAddress> {
            // TODO: バリデーションを実装、コメントを修正
            if (storeAddress.isEmpty() || storeAddress.isBlank()) {
                return ValidationError("ストア住所が条件を満たしていません").left()
            }
            return StoreAddress(storeAddress).right()
        }
    }
}



