package pl.otekplay.ranking

internal class RankingComparator : Comparator<Ranking> {
    override fun compare(o1: Ranking, o2: Ranking) = Integer.compare(o2.points, o1.points)

}