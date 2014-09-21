trait DBSettings {
  DBSettings.initialize()
}

object DBSettings {

  private var initialized = false

  def initialize(): Unit = {
    if (initialized) return
    scalikejdbc.config.DBs.setupAll()
    initialized = true
  }
}
