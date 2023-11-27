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
 * @property storeAddress 住所
 */
data class Store(val storeId: StoreId, val storeName: StoreName,val storeAddress: StoreAddress)

fun createStore(storeId: String, storeName: String, storeAddress: String): ValidatedNel<ValidationError, Store> {
    return StoreId.create(storeId).zip(StoreName.create(storeName), StoreAddress.create(storeAddress)){
        validatedStoreId, validatedStoreName, validatedStoreAddress -> Store(validatedStoreId, validatedStoreName, validatedStoreAddress)
    }
}

class StoreId private constructor(val value: String){
    companion object {
        /**
         * ストアIDオブジェクトのファクトリ
         *
         * @param storeId ストアID
         * @rerun 失敗：バリデーションエラー、成功：ストアIDオブジェクト
         */
        fun create(storeId: String): ValidatedNel<ValidationError, StoreId>{
            // TODO: バリデーションを実装、コメントを修正
            if (storeId.isEmpty() || storeId.isBlank()){
                return ValidationError("ストアIDが条件を満たしていません").invalidNel()
            }
            return StoreId(storeId).valid()
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
        fun create(storeName: String): ValidatedNel<ValidationError, StoreName>{
            // TODO: バリデーションを実装、コメントを修正
            if (storeName.isEmpty() || storeName.isBlank()){
                return ValidationError("ストア名が条件を満たしていません").invalidNel()
            }
            return StoreName(storeName).valid()
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
        fun create(storeAddress: String): ValidatedNel<ValidationError, StoreAddress> {
            // TODO: バリデーションを実装、コメントを修正
            if (storeAddress.isEmpty() || storeAddress.isBlank()) {
                return ValidationError("ストア住所が条件を満たしていません").invalidNel()
            }
            return StoreAddress(storeAddress).valid()
        }
    }
}



