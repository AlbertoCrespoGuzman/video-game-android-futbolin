package com.androidsrc.futbolin.communications.http.auth.get;

import java.util.List;

public class getTournament {

    Tournament tournament;
    Category category;
    NextMatch next_match;
    private List<LastMatch> last_matches;

    List<TournamentCategories> categories;

    public getTournament(){}

    public Tournament getTournament() {
        return tournament;
    }

    public List<TournamentCategories> getCategories() {
        return categories;
    }

    public void setCategories(List<TournamentCategories> categories) {
        this.categories = categories;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public NextMatch getNext_match() {
        return next_match;
    }

    public void setNext_match(NextMatch next_match) {
        this.next_match = next_match;
    }

    public List<LastMatch> getLast_matches() {
        return last_matches;
    }

    public void setLast_matches(List<LastMatch> last_matches) {
        this.last_matches = last_matches;
    }

    @Override
    public String toString() {
        return "getTournament{" +
                "tournament=" + tournament +
                ", category=" + category +
                ", next_match=" + next_match +
                ", last_matches=" + last_matches +
                '}';
    }
}
