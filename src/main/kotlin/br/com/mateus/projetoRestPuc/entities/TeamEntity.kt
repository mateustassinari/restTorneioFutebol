package br.com.mateus.projetoRestPuc.entities

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import io.swagger.annotations.ApiModelProperty
import java.sql.Date
import javax.persistence.*

@Entity
@Table(name = "Times", catalog = "")
data class TeamEntity (
    @get:Id
    @get:GeneratedValue(strategy=GenerationType.IDENTITY)
    @get:Column(name = "id", nullable = false, insertable = false, updatable = false)
    var id: Int? = null,
    @get:Basic
    @get:Column(name = "nome", nullable = false)
    var name: String? = null,
    @get:Basic
    @get:Column(name = "siglaEstado", nullable = false)
    var uf: String? = null,
    @get:Basic
    @get:Column(name = "cidade", nullable = false)
    var city: String? = null,
    @get:Basic
    @get:Column(name = "dataFundacao", nullable = false)
    @JsonFormat(pattern="dd/MM/yyyy")
    @ApiModelProperty(dataType = "java.sql.Date")
    var foundingDate: Date? = null,

    @get:OneToMany(mappedBy = "playerTeam")
    @JsonIgnore
    var players: List<PlayerEntity>? = null,
    @get:OneToMany(mappedBy = "originTeam", cascade = [CascadeType.REMOVE])
    @JsonIgnore
    var originTransfers: List<TransferEntity>? = null,
    @get:OneToMany(mappedBy = "destinyTeam", cascade = [CascadeType.REMOVE])
    @JsonIgnore
    var destinyTransfers: List<TransferEntity>? = null,

    @get:OneToMany(mappedBy = "matchAwayTeam", cascade = [CascadeType.REMOVE])
    @JsonIgnore
    var awayMatches: List<MatchEntity>? = null,
    @get:OneToMany(mappedBy = "matchHomeTeam", cascade = [CascadeType.REMOVE])
    @JsonIgnore
    var homeMatches: List<MatchEntity>? = null,


    @get:ManyToMany(mappedBy="teamsTournament")
    @JsonIgnore
    var tournamentsTeam: MutableList<TournamentEntity>? = null

    ) {

    @PreRemove
    fun removeTeam() {
        players?.forEach { player -> player.playerTeam = null }
        tournamentsTeam?.forEach { t -> this.let { t.removeTournamentsTeam(it) } }
    }
}

