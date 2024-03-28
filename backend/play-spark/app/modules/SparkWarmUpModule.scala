package modules

import play.api.inject.{SimpleModule, bind}
import schedulers.SparkWarmUp

class SparkWarmUpModule extends SimpleModule(bind[SparkWarmUp].toSelf.eagerly())
