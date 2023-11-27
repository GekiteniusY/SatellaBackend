package jp.stella.uncategorizable

/**
 * ドメインオブジェクトのバリデーションにおけるエラー型
 *
 * 必ずエラーメッセージを記述する
 */

//interface ValidationError{
//    /**
//     * エラーメッセージ
//     */
//    val errorMessage: String
//}

data class ValidationError(val errorMessage: String)