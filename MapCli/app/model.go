package app

import "encoding/json"

type Model struct {
	name                  string
	author                string
	mapDirName            string
	islandSpawn           [][]int
	minLayer              int
	islandChests          [][]int
	otherChests           [][]int
	countDownSeconds      int
	countDownMinPlayers   int
	countDownEnd          int
	maxTimerRepetitions   int
	countDownTimerSeconds int
}

func NewModel() *Model {
	return &Model{}
}

func (m *Model) Name() string {
	return m.name
}

func (m *Model) SetName(v string) {
	m.name = v
}

func (m *Model) Author() string {
	return m.author
}

func (m *Model) SetAuthor(v string) {
	m.author = v
}

func (m *Model) MapDirName() string {
	return m.mapDirName
}

func (m *Model) SetMapDirName(v string) {
	m.mapDirName = v
}

func (m *Model) AddIslandSpawn(x, y, z int) {
	m.islandSpawn = append(m.islandSpawn, []int{x, y, z})
}

func (m *Model) IslandSpawns() [][]int {
	return m.islandSpawn
}

func (m *Model) MinLayer() int {
	return m.minLayer
}

func (m *Model) SetMinLayer(v int) {
	m.minLayer = v
}

func (m *Model) AddIslandChest(x, y, z int) {
	m.islandChests = append(m.islandChests, []int{x, y, z})
}

func (m *Model) IslandChests() [][]int {
	return m.islandChests
}

func (m *Model) AddOtherChest(x, y, z int) {
	m.otherChests = append(m.otherChests, []int{x, y, z})
}

func (m *Model) OtherChests() [][]int {
	return m.otherChests
}

func (m *Model) CountDownSeconds() int {
	return m.countDownSeconds
}

func (m *Model) SetCountDownSeconds(v int) {
	m.countDownSeconds = v
}

func (m *Model) CountDownMinPlayers() int {
	return m.countDownMinPlayers
}

func (m *Model) SetCountDownMinPlayers(v int) {
	m.countDownMinPlayers = v
}

func (m *Model) CountDownEnd() int {
	return m.countDownEnd
}

func (m *Model) SetCountDownEnd(v int) {
	m.countDownEnd = v
}

func (m *Model) MaxTimerRepetitions() int {
	return m.maxTimerRepetitions
}

func (m *Model) SetMaxTimerRepetitions(v int) {
	m.maxTimerRepetitions = v
}

func (m *Model) CountDownTimerSeconds() int {
	return m.countDownTimerSeconds
}

func (m *Model) SetCountDownTimerSeconds(v int) {
	m.countDownTimerSeconds = v
}

func (m *Model) AsJson() ([]byte, error) {
	return json.Marshal(m)
}
