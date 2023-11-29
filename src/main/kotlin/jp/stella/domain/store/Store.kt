package jp.stella.domain.store

import arrow.core.*
import jp.stella.uncategorizable.ValidationError


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




interface StoreAddress {
    /**
     * StoreAddressの値
     */
    val value: String

    // TODO: 住所ドメインのルールを記述する
    /**
     * StoreAddress 生成時のドメインルール
     *
     * ルール１：
     * ルール２：
     */
    sealed interface CreationErrorStoreAddress: ValidationError {
        /**
         * 必須
         *
         * Null は許容しない
         *
         * @constructor Create empty Required
         */
        data object Required : CreationErrorStoreAddress {
            override val errorMessage: String
                get() = "StoreAddress は必須です"
        }

        data class InValidFormat(val storeAddress: String): CreationErrorStoreAddress {
            override val errorMessage: String
                // TODO: 住所ドメインのルールを反映させる
                get() = "StoreAddressは✗✗でなければいけません"
        }
    }


    /**
     * new から生成された StoreAddress
     *
     * @property value
     */
    private class ValidatedStoreAddress(override val value: String): StoreAddress

    /**
     * new 以外から生成された StoreAddress
     *
     * @property
     */
    private data class NotValidatedStoreAddress(override val value: String): StoreAddress

    companion object {
        // TODO: 住所入力時のフォーマットを正規表現で指定
        // 現在入力されている値はサンプル
        private const val FORMAT: String = "^[a-z0-9]{32}$"

        /**
         * Validation 無しのインスタンス生成
         *
         * @param address
         * @return
         */
        fun newNotValidatedStoreAddress(address: String): StoreAddress = NotValidatedStoreAddress(address)

        /**
         * Validation 有りのインスタンス生成
         *
         * @param storeAddress
         * @return
         */
        fun new(storeAddress: String?): ValidatedNel<CreationErrorStoreAddress, StoreAddress> {
            /**
             * null チェック
             *
             * 空白だった場合、早期リターン
             */
            if (storeAddress == null) {
                return CreationErrorStoreAddress.Required.invalidNel()
            }

            /**
             * FORMAT チェック
             *
             * FORMAT が適切でなかったら、早期リターン
             */
            if (!storeAddress.matches(Regex(FORMAT))){
                return CreationErrorStoreAddress.InValidFormat(storeAddress).invalidNel()
            }

            // OK
            return ValidatedStoreAddress(storeAddress).validNel()
        }
    }

}




//fun createStore(storeId: String, storeName: String, storeAddress: String): ValidatedNel<ValidationError, Store> {
//    return StoreId.create(storeId).zip(StoreName.create(storeName), StoreAddress.create(storeAddress)){
//        validatedStoreId, validatedStoreName, validatedStoreAddress -> Store(validatedStoreId, validatedStoreName, validatedStoreAddress)
//    }
//}
//
//class StoreId private constructor(val value: String){
//    companion object {
//        /**
//         * ストアIDオブジェクトのファクトリ
//         *
//         * @param storeId ストアID
//         * @rerun 失敗：バリデーションエラー、成功：ストアIDオブジェクト
//         */
//        fun create(storeId: String): ValidatedNel<ValidationError, StoreId>{
//            // TODO: バリデーションを実装、コメントを修正
//            if (storeId.isEmpty() || storeId.isBlank()){
//                return ValidationError("ストアIDが条件を満たしていません").invalidNel()
//            }
//            return StoreId(storeId).valid()
//        }
//    }
//}
//
//
//class StoreName private constructor(val value: String){
//    companion object {
//        /**
//         * ストア名オブジェクトのファクトリ
//         *　
//         * @param storeName ストア名
//         * @return 失敗：バリデーションエラー、成功：ストア名オブジェクト
//         */
//        fun create(storeName: String): ValidatedNel<ValidationError, StoreName>{
//            // TODO: バリデーションを実装、コメントを修正
//            if (storeName.isEmpty() || storeName.isBlank()){
//                return ValidationError("ストア名が条件を満たしていません").invalidNel()
//            }
//            return StoreName(storeName).valid()
//        }
//    }
//}
//
//class StoreAddress private constructor(val value: String) {
//    companion object {
//        /**
//         * ストア住所オブジェクトのファクトリ
//         *
//         * @param storeAddress ストア住所
//         * @return 失敗：バリデーションエラー、成功：ストア住所オブジェクト
//         */
//        fun create(storeAddress: String): ValidatedNel<ValidationError, StoreAddress> {
//            // TODO: バリデーションを実装、コメントを修正
//            if (storeAddress.isEmpty() || storeAddress.isBlank()) {
//                return ValidationError("ストア住所が条件を満たしていません").invalidNel()
//            }
//            return StoreAddress(storeAddress).valid()
//        }
//    }
//}