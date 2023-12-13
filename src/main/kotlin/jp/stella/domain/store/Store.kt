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

interface StoreId {
    /**
     * StoreId の値
     */
    val value : String

    /**
     * new で生成するStoreId
     *
     * @param value
     */
    private data class ValidatedStoreId(override val value: String): StoreId

    /**
     * newNotValidatedStoreId で生成するStoreId
     * DBからのデータ生成時に利用
     *
     * @param value
     */
    private data class NotValidatedStoreId(override val value:String): StoreId


    // Factory
    companion object {
        // TODO: StoreIDのフォーマットを正規表現で指定
        // 現在入力されている値はサンプル
        private const val FORMAT: String = "^[a-z0-9]{32}$"

        fun newNotValidatedStoreId(storeId: String): ValidatedNel<CreationErrorStoreId, StoreId> = NotValidatedStoreId(storeId).validNel()
        fun new(storeId: String?): ValidatedNel<CreationErrorStoreId, StoreId>{
            /**
             * nullチェック
             *
             * 空白だった場合は早期リターン
             */
            if(storeId == null){
                return CreationErrorStoreId.Required.invalidNel()
            }

            /**
             * FORMATチェック
             *
             * FORMATが適切でなかったら早期リターン
             */
            if(storeId.matches(Regex(FORMAT))){
                return CreationErrorStoreId.InvalidFormat(storeId).invalidNel()
            }

            // OK
            return ValidatedStoreId(storeId).validNel()
        }
    }


    /**
     * ドメインルールエラー
     */
    sealed interface CreationErrorStoreId: ValidationError {
        /**
         * 必須
         *
         * Null は許容しない
         *
         * @constructor Create empty Required
         */
        data object Required: StoreId.CreationErrorStoreId {
            override val errorMessage: String
                get() = "StoreName は必須です"

        }

        /**
         * フォーマット確認
         *
         * 指定されたFORMAT以外はだめ
         *
         * @property storeId
         */
        // TODO: StoreIDのフォーマット
        data class InvalidFormat(val storeId: String): StoreId.CreationErrorStoreId {
            override val errorMessage: String
                get() = "$storeId は条件を満たしていません。ストア名は✗✗である必要があります。"

        }



    }
}

interface StoreName {
    /**
     * StoreName の値
     */
    val value: String


    /**
     * new で生成するStoreName
     *
     * @property value
     */
    private class ValidatedStoreName(override val value: String): StoreName

    /**
     * newNotValidatedStoreName で生成するStoreName
     * DBからのデータ生成時に利用
     *
     * @property value
     */
    private class NotValidatedStoreName(override val value: String): StoreName

    companion object{
        // TODO: 住所入力時のフォーマットを正規表現で指定
        // 現在入力されている値はサンプル
        private const val FORMAT: String = "^[a-z0-9]{32}$"

        fun newNotValidatedStoreName(storeName: String): ValidatedNel<CreationErrorStoreName, StoreName> = NotValidatedStoreName(storeName).validNel()

        fun new(storeName: String?): ValidatedNel<CreationErrorStoreName, StoreName>{
            /**
             * nullチェック
             *
             * 空白だった場合は早期リターン
             */
            if (storeName == null){
                return CreationErrorStoreName.Required.invalidNel()
            }

            /**
             * FORMATチェック
             *
             * FORMATが適切でなかったら早期リターン
             */
            if (!storeName.matches(Regex(FORMAT))){
                return CreationErrorStoreName.InvalidFormat(storeName).invalidNel()
            }

            // OK
            return ValidatedStoreName(storeName).validNel()
        }
    }

    // TODO: ストア名ルールを記述する
    /**
     * StoreName 生成時のドメインルールエラー
     *
     * ルール１：SomeRule1
     * ルール２：SomeRule2
     */
    sealed interface CreationErrorStoreName: ValidationError{
        /**
         * 必須
         *
         * Null は許容しない
         *
         * @constructor Create empty Required
         */
        data object Required: CreationErrorStoreName{
            override val errorMessage: String
                get() = "StoreName は必須です"

        }

        /**
         * フォーマット確認
         *
         * 指定されたFORMAT以外はだめ
         *
         * @property storeName
         */
        data class InvalidFormat(val storeName: String): CreationErrorStoreName{
            override val errorMessage: String
                get() = "$storeName は条件を満たしていません。ストア名は✗✗である必要があります。"

        }


    }

}

interface StoreAddress {
    /**
     * StoreAddressの値
     */
    val value: String

    /**
     * new で生成するStoreAddress
     *
     * @property value
     */
    private class ValidatedStoreAddress(override val value: String): StoreAddress

    /**
     * newNotValidatedStoreAddress で生成するStoreAddress
     * DBからのデータ生成時に利用
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

    // TODO: 住所ドメインのルールを記述する
    /**
     * StoreAddress 生成時のドメインルールエラー
     *
     * ルール１：SomeRule1
     * ルール２：SomeRule2
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

        /**
         * フォーマット確認
         *
         * 指定されたFORMAT以外はだめ
         *
         * @property storeAddress
         */
        data class InValidFormat(val storeAddress: String): CreationErrorStoreAddress {
            override val errorMessage: String
                // TODO: 住所ドメインのルールを反映させる
                get() = "$storeAddress は条件を満たしていません。StoreAddressは✗✗でなければいけません"
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