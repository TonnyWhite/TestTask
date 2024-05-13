package mapper

import kotlin.reflect.KClass

/**
 * Mapper used to map input [I] to output [O].
 * @see StringMapper
 * @see URLMapper
 * @see URIMapper
 */
interface Mapper<I : Any, O : Any> {

    val inputKClass: KClass<I>
    val outputKClass: KClass<O>

    /**
     * Maps input [I] to output [O].
     */
     fun map(input: I): O

    /**
     * Whether mapping [I] is supported or not.
     */
     val I.isSupported: Boolean
        get() = true
}