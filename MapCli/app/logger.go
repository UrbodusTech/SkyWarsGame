package app

import "github.com/sirupsen/logrus"

var Logger *logrus.Logger

func BindLogger() {
	Logger = logrus.New()
	Logger.Formatter = &logrus.TextFormatter{ForceColors: true}
	Logger.Level = logrus.InfoLevel
	Logger.WithField("prefix", "MapCLI")
}
