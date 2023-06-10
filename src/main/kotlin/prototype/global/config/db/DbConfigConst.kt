package prototype.global.config.db

object DbConfigConst {
    private const val domainPackage = "prototype.domain"
    const val configPrefix = "spring.datasource.hikari"
    const val repositoryBasePackage = "$domainPackage.repository"
    const val entityBasePackage = "$domainPackage.entity"
}