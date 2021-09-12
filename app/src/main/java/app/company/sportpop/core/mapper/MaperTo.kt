package app.company.sportpop.core.mapper

interface MapperTo<in T, out R> {
    fun mapTo(t: T): R
}
