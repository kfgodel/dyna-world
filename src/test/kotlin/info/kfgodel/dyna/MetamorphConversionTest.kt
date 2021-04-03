package info.kfgodel.dyna

import com.google.common.collect.ImmutableMap
import com.google.common.collect.Maps
import info.kfgodel.dyna.impl.DefaultEnvironment
import info.kfgodel.dyna.objects.HumanLycanthrope
import info.kfgodel.dyna.objects.WolfLycanthrope
import info.kfgodel.jspek.api.JavaSpecRunner
import info.kfgodel.jspek.api.KotlinSpec
import org.assertj.core.api.Assertions
import org.junit.runner.RunWith

/**
 * Date: 28/3/21 - 02:00
 */
@RunWith(JavaSpecRunner::class)
class MetamorphConversionTest : KotlinSpec() {
  override fun define() {
    describe("given a default environment") {
      val environment by let { DefaultEnvironment.create() }

      describe("and initial state") {
        val initialState by let {
          Maps.newHashMap(
            ImmutableMap.builder<String, Any>()
              .put("name", "Scott Howard")
              .put("height", 163)
              .build()
          )
        }

        describe("a metamorph object") {
          val metamorph by let { environment().creator().create(HumanLycanthrope::class.java, initialState()) }

          describe("when becoming other type") {
            val morphed by let { metamorph().become(WolfLycanthrope::class.java) }

            it("changes its type") {
              Assertions.assertThat<WolfLycanthrope>(morphed()).isNotNull()
              Assertions.assertThat<WolfLycanthrope>(morphed()).isNotInstanceOf(HumanLycanthrope::class.java)
            }
            it("shares its state with the morphed object") {
              Assertions.assertThat(morphed().name).isEqualTo("Scott Howard")
              Assertions.assertThat<String, Any>(metamorph().getInternalState())
                .isSameAs(morphed().getInternalState())
            }
            it("is affected by morphed object state changes") {
              morphed().receiveSilverBullet()
              Assertions.assertThat(morphed().dead).isTrue()
              Assertions.assertThat(metamorph().dead).isTrue()
            }
            it("allows different behavior for the same state") {
              Assertions.assertThat(metamorph().jumpHeight).isEqualTo(81)
              Assertions.assertThat(morphed().jumpHeight).isEqualTo(326)
            }
            it("is equal and has same hashcode as morphed object") {
              Assertions.assertThat<HumanLycanthrope>(metamorph()).isEqualTo(morphed())
              Assertions.assertThat(metamorph().hashCode()).isEqualTo(morphed().hashCode())
            }
          }
        }

      }
    }
  }
}
